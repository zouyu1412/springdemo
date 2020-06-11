package com.ssp.netty.dubbo.loadbalance;

import java.util.List;
import java.util.Random;

// 随机负载均衡算法
public class RandomLoadBalance implements LoadBalance {
    @Override
    public String choose(List<String> providers) {
        return providers.get(new Random().nextInt(providers.size()));
    }
}
