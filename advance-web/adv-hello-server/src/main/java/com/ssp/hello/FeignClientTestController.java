package com.ssp.hello;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:zouyu
 * @Date:2019/11/28 10:02
 */
@RestController
@RequestMapping("feign-service")
public class FeignClientTestController {

    @RequestMapping(value="/instance/{serviceId}",method = RequestMethod.GET)
    public String getValue(@PathVariable("serviceId")String serviceId){
        return serviceId+" here we go";
    }

}
