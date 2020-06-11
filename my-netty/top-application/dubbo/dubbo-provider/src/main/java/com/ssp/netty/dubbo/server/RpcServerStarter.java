package com.ssp.netty.dubbo.server;

import com.abc.register.RegistryCenter;
import com.abc.register.ZkRegistryCenter;

public class RpcServerStarter {
    public static void main(String[] args) throws Exception {
        RegistryCenter center = new ZkRegistryCenter();

        RpcServer server = new RpcServer();
        String serviceAddress = "127.0.0.1:9999";
        String providerPackage = "com.abc.service";
        server.publish(providerPackage, center, serviceAddress);
        server.start(serviceAddress);
    }
}
