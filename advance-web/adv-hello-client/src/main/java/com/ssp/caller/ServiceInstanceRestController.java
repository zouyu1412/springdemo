package com.ssp.caller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:zouyu
 * @Date:2019/11/25 17:38
 */
@RestController
public class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instance/{applicationName}")
    public List<ServiceInstance> serviceInstances(@PathVariable("applicationName")String applicationName){
        return this.discoveryClient.getInstances(applicationName);
    }

}
