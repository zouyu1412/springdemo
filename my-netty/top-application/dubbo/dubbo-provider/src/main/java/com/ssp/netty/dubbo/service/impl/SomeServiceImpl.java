package com.ssp.netty.dubbo.service.impl;


import com.ssp.netty.dubbo.service.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        return "hello Netty RPC World";
    }
}
