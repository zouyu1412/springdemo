package com.ssp.higher.business.myservice;


import com.ssp.higher.base.pojos.Result;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author:zouyu
 * @Date:2019/9/25 14:49
 */
@FeignClient(name = "myFeign1", url = "http://db.auto.sohu.com/wechat/wx/user")
public interface FeignService {

    @RequestMapping(value = "/record/cache",method = RequestMethod.GET)
    Result test(@RequestParam("openId")int openId, @RequestParam("modelId") int modelId);

}
