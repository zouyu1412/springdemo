package com.ssp.netty.rpcclient.client;

import com.ssp.netty.rpcbase.InvokeMessage;
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
    private static Object rpcInvoke(Class<?> clazz, Method method, Object[] args) throws InterruptedException {
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
            ChannelFuture future = bootstrap.connect("localhost", 8888).sync();

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
