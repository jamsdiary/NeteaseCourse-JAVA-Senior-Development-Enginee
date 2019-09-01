package com.study.order.client;

import com.study.order.bean.FileUploadResult;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhongxin on 2017/9/17.
 */

@FeignClient(value = "cloud-oss", configuration=CloudOssClient.MultipartSupportConfig.class)
public interface CloudOssClient {

//    @GetMapping(value = "/file/upload")
//    FileUploadResult fileUpload(@RequestParam("file") MultipartFile file);

    @PostMapping(value="/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResult fileUpload(@RequestPart MultipartFile file);

    @GetMapping(value="file/delete")
    FileUploadResult removeFile(@RequestParam("fileName") String image);

    class MultipartSupportConfig extends FeignClientsConfiguration {
        @Bean
        public Encoder multipartFormEncoder() {
            return new SpringFormEncoder();
        }
    }

}



