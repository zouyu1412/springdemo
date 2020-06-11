package com.ssp.netty.dubbo.register;

public class RegisterTest {
    public static void main(String[] args) throws Exception {
        RegistryCenter center = new ZkRegistryCenter();
        center.register("com.abc.service.SomeService", "127.0.0.1:8888");
        System.in.read();
    }
}
