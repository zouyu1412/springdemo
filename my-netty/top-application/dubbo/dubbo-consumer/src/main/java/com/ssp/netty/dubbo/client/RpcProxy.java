package com.ssp.netty.dubbo.client;


import com.ssp.netty.dubbo.discovery.ServiceDiscovery;
import com.ssp.netty.dubbo.discovery.ServiceDiscoveryImpl;
import com.ssp.netty.dubbo.service.InvokeMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    // 泛型方法
    public static <T> T create(Class<?> clazz) {
        return(T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 若调用的是Object的方法，则直接进行本地调用
                        if(Object.class.equals(method.getDeclaringClass())) {
                            return method.invoke(this, args);
                        }
                        //远程调用
                        return rpcInvoke(clazz, method, args);
                    }
                });
    }

    //远程调用
    private static Object rpcInvoke(Class<?> clazz, Method method, Object[] args) throws Exception {
        ServiceDiscovery discovery = new ServiceDiscoveryImpl();
        String serviceAddress = discovery.discovery(clazz.getName());
        if(serviceAddress == null) {
            return null;
        }

        RpcClientHandler handler = new RpcClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    // Nagle算法
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            // 添加自定义处理器
                            pipeline.addLast(handler);
                        }
                    });

            // 解析出Host与port
            String host = serviceAddress.split(":")[0];
            String portStr = serviceAddress.split(":")[1];
            Integer port = Integer.valueOf(portStr);

            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 将远程调用信息发送给Server端
            InvokeMessage message = new InvokeMessage();
            message.setClassName(clazz.getName());
            message.setMethodName(method.getName());
            message.setParamTypes(method.getParameterTypes());
            message.setParamValues(args);

            future.channel().writeAndFlush(message).sync();


            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        // 返回远程调用结果
        return handler.getResult();
    }
}
