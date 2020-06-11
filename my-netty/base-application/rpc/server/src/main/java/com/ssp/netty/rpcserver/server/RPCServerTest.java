package com.ssp.netty.rpcserver.server;

public class RPCServerTest {
    public static void main(String[] args) {
        new RpcServer().getProviderClass("com.abc.service");
    }
}
