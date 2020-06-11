package com.ssp.netty.dubbo.server;

public class GetProviderClassTest {
    public static void main(String[] args) {
        new RpcServer().getProviderClass("com.abc.rpc.service");
    }
}
