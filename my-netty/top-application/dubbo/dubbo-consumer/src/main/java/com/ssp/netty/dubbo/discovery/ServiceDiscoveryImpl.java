package com.ssp.netty.dubbo.discovery;

import com.abc.constant.ZKConstant;
import com.abc.loadbalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class ServiceDiscoveryImpl implements ServiceDiscovery {
    private List<String> providers;

    private CuratorFramework curator;

    public ServiceDiscoveryImpl() {
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
    public String discovery(String serviceName) throws Exception {
        String servicePath = ZKConstant.ZK_DUBBO_ROOT + "/" + serviceName;
        List<String> providers = curator.getChildren().forPath(servicePath);
        if (providers.size() == 0) {
            return null;
        }

        // 为servicePath添加子节点列表变更的watcher监听
        registerWatcher(servicePath);
        return new RandomLoadBalance().choose(providers);
    }

    private void registerWatcher(String servicePath) throws Exception {
        // cache中缓存了指定节点的子节点数据
        PathChildrenCache cache = new PathChildrenCache(curator, servicePath, true);
        // 为cache添加监听器，监听子节点列表的变更
        cache.getListenable().addListener((client, event) -> {
            // 当子节点列表有变更，则马上更新providers
            providers = client.getChildren().forPath(servicePath);
        });

        // 启动监听
        cache.start();
    }
}
