package com.ssp.netty.rpcserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.net.URL;
import java.util.*;

// 我们要将指定接口下的业务接口实现类与其对应的接口名称（服务名称）形成对应关系
// 写入到一个注册表中
public class RpcServer {
    // 注册表（在网络项目中用到的集合需要考虑线程安全问题）
    private Map<String, Object> registerMap = new HashMap<>();
    // 该集合用于存放指定包中所有业务接口的实现类名
    // private List<String> classCache = Collections.synchronizedList(new ArrayList<>());
    private List<String> classCache = new ArrayList<>();

    // 将指定包中的所有业务接口实现类（提供者）进行发布（写入到注册表中）
    public void publish(String providerPackage) throws Exception {
        // 获取到指定包中的所有类名
        getProviderClass(providerPackage);
        // 写入到注册表
        doRegister();
    }

    public void getProviderClass(String providerPackage) {
        // 获取指定目录中的所有资源
        URL resource = this.getClass().getClassLoader()
                // 将包中的点号(.)替换为路径中的斜杠(/)
                .getResource(providerPackage.replaceAll("\\.","/"));

        // 若指定目录中没有资源，则直接结束
        if(resource == null) return;

        File dir = new File(resource.getFile());
        // 遍历dir目录下的所有文件，查找.class文件
        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                // 递归子目录
                getProviderClass(providerPackage + "." + file.getName());
            } else if(file.getName().endsWith(".class")) {
                // 将全限定性类名写入到classCache
                String fileName = file.getName().replace(".class", "").trim();
                classCache.add(providerPackage + "." + fileName);
            }
        }
        // System.out.println("classCache = " + classCache);
    }

    private void doRegister() throws Exception {

        if(classCache.size() == 0) return;

        for (String className : classCache) {
            Class<?> aClass = Class.forName(className);
            // 获取业务接口名称
            String interfaceName = aClass.getInterfaces()[0].getName();
            // 将业务接口名作为key，实现类实例作为value，写入到注册表
            registerMap.put(interfaceName, aClass.newInstance());
        }
    }

    // Server的启动方法
    public void start() throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            // 添加自定义处理器
                            pipeline.addLast(new RpcServerHandler(registerMap));
                        }
                    });

            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("提供者启动");
            future.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
