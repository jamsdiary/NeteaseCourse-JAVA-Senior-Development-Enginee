package com.study.order.client;

import com.study.order.client.hystrix.CloudAdminHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Created by zhongxin on 2017/9/17.
 */

@FeignClient(value = "cloud-admin",fallback = CloudAdminHystrix.class )
public interface CloudAdminClient {

    @GetMapping(value = "/user/names")
    Map<Integer,String> getUserNames();

}



