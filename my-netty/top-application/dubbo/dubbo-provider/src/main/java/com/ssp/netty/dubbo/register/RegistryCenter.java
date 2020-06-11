package com.ssp.netty.dubbo.register;

public interface RegistryCenter {
    /**
     * 服务注册规范
     * @param serviceName 微服务名称
     * @param serviceAddress ip:port
     */
    void register(String serviceName, String serviceAddress) throws Exception;
}
