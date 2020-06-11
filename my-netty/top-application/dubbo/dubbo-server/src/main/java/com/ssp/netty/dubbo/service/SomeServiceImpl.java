package com.ssp.netty.dubbo.service;

import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService {
    @Override
    public String hello(String name) {
        return "Hello," + name;
    }
}
