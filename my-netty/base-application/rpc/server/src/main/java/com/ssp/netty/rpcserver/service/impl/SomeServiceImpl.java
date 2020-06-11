package com.ssp.netty.rpcserver.service.impl;

import com.ssp.netty.rpcbase.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        return "hello Netty RPC World";
    }
}
