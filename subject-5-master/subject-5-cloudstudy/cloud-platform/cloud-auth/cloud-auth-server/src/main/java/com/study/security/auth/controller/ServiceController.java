package com.study.security.auth.controller;

import com.study.security.auth.biz.ClientBiz;
import com.study.security.auth.entity.Client;
import com.study.security.auth.entity.ClientService;
import com.study.security.common.msg.ObjectRestResponse;
import com.study.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ServiceController
 * @Description Service控制器
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/6/11 15:39
 * @Version 1.0
 */
@RestController
@RequestMapping("service")
public class ServiceController extends BaseController<ClientBiz, Client> {

    @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable int id, String clients) {
        baseBiz.modifyClientServices(id, clients);
        return new ObjectRestResponse().rel(true);
    }

    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<ClientService> getUsers(@PathVariable int id) {
        return new ObjectRestResponse<ClientService>().rel(true).data(baseBiz.getClientServices(id));
    }
}
