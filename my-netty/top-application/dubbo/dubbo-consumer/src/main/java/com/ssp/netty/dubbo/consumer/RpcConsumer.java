package com.ssp.netty.dubbo.consumer;


import com.ssp.netty.dubbo.client.RpcProxy;
import com.ssp.netty.dubbo.service.SomeService;

public class RpcConsumer {
    public static void main(String[] args) {
        SomeService service = RpcProxy.create(SomeService.class);
        if(service != null) {
            System.out.println(service.hello("开课吧"));
            System.out.println(service.hashCode());
        }
    }
}
