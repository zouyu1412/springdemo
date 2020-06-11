package com.ssp.netty.dubbo.discovery;

public class DiscoveryTest {
    public static void main(String[] args) throws Exception {
        ServiceDiscovery discovery = new ServiceDiscoveryImpl();
        System.out.println(discovery.discovery("com.abc.service.SomeService"));
    }
}
