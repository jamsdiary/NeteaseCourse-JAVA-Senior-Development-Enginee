package com.study.security.gate.feign;

import com.study.security.api.vo.log.LogInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName ILogService
 * @Description 日志feign
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/6/11 15:39
 * @Version 1.0
 */
@FeignClient("cloud-admin")
public interface ILogService {
    @RequestMapping(value = "/api/log/save", method = RequestMethod.POST)
    void saveLog(LogInfo info);
}
