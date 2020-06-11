package com.ssp.netty.rpcclient.consumer;


import com.ssp.netty.rpcbase.SomeService;
import com.ssp.netty.rpcclient.client.RpcProxy;

public class RpcConsumer {
    public static void main(String[] args) {
        SomeService service = RpcProxy.create(SomeService.class);
        System.out.println(service.hello("开课吧"));
        System.out.println(service.hashCode());
    }
}
