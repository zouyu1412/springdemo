package com.ssp.caller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author:zouyu
 * @Date:2020/1/9 15:07
 */
@Service
public class InstanceService {

    private Logger logger = LoggerFactory.getLogger(InstanceService.class);

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "instanceInfoGetFail")
    public Instance getInstanceByServiceId(String serviceId){
        Instance instance = restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}",Instance.class,serviceId).getBody();
        return instance;
    }

    private Instance instanceInfoGetFail(String serviceId){
        logger.info("can not get instance by service id[{}]",serviceId);
        return new Instance(-1,"giao",null);
    }
}
