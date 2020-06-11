package com.ssp.netty.dubbo.discovery;

public interface ServiceDiscovery {
    /**
     *   服务发现
     * @param serviceName 要从注册中心查找的微服务名称
     * @return 返回该微服务名称提供者经负载均衡后的一台主机地址
     */
    String discovery(String serviceName) throws Exception;
}
