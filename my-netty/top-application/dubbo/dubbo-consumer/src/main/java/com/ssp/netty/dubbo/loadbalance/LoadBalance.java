package com.ssp.netty.dubbo.loadbalance;

import java.util.List;


public interface LoadBalance {
    /**
     *  从providers中通过负载均衡算法找到一个provider
     * @param providers
     * @return
     */
    String choose(List<String> providers);
}
