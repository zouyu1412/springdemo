package com.ssp.netty.dubbo.register;

import com.abc.constant.ZKConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.PrintWriter;

public class ZkRegistryCenter implements RegistryCenter {

    private CuratorFramework curator;

    public ZkRegistryCenter() {
        curator = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.ZK_CLUSTER)
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(3000)
                // 设置重试机制：每1秒重试一次，最多重试10次
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        // 启动zk客户端
        curator.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        // 1.创建微服务名称的持久节点

        String servicePath = ZKConstant.ZK_DUBBO_ROOT + "/" + serviceName;
        // 若该微服务对应的节点不存在，则创建
        if(curator.checkExists().forPath(servicePath) == null) {
            curator.create()
                    // 若父节点不存在，则创建父节点
                    .creatingParentsIfNeeded()
                    // 创建持久节点
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(servicePath);
        }


        // 2.创建当前提供者主机在zk中对应的临时节点
        String addressPath = servicePath + "/" + serviceAddress;
        if(curator.checkExists().forPath(addressPath) == null) {
            String addressNode = curator.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
            System.out.println("Provider Node Create " + addressNode);
        }
    }
}
