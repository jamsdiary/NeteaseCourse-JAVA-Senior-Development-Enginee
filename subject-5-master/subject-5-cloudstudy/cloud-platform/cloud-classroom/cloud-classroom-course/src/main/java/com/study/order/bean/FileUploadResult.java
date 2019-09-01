package com.study.order.bean;

import lombok.Data;

/**
 * @author andy
 * @desc 用于前后端交互的返回值
 * @email xo@oo.com
 */
@Data
public class FileUploadResult {
    // 文件唯一标识
    private String uid;
    // 全路径URL 文件名
    private String name;
    // 状态有：uploading done error removed
    private String status;
    // 服务端响应内容，如：'{"status": "success"}'
    private String response;
}
