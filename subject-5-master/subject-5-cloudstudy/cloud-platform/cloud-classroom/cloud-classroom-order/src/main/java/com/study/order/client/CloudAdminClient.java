package com.study.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@FeignClient(value = "cloud-admin")
public interface CloudAdminClient {

    @GetMapping(value = "/user/names")
    Map<Integer,String> getUserNames();

}



