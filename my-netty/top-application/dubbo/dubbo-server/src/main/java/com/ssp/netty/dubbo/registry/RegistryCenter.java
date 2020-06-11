package com.ssp.netty.dubbo.registry;

// 定义注册规范
public interface RegistryCenter {
    /**
     *  向zk中注册服务
     * @param serviceName  服务名称，即业务接口名
     * @param serviceAddress  提供者主机地址，即ip:port
     */
    void register(String serviceName, String serviceAddress) throws Exception;
}
