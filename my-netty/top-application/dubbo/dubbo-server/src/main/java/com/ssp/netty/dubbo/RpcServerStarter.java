package com.ssp.netty.dubbo;

import com.abc.rpc.registry.RegistryCenter;
import com.abc.rpc.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcServerStarter implements CommandLineRunner {
    @Autowired
    private RpcServer server;
    @Autowired
    private RegistryCenter registryCenter;

    public static void main(String[] args) {
        SpringApplication.run(RpcServerStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 将指定包下的提供者发布到服务器
        server.publish(registryCenter, "localhost:8888", "com.abc.rpc.service");
        // 启动服务器
        server.start();
    }
}
