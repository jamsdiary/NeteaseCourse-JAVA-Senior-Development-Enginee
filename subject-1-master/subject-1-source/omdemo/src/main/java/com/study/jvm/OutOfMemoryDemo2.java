package com.study.jvm;

// 内存
public class OutOfMemoryDemo2 {
    public static void main(String[] args) throws Exception {
        // 1、 收到告警，保障可用性 （重启）
        // 2、 重启之前保留现场

        // 由于参数校验不严谨 导致的问题
        // 问题功能描述：查询列表数据.总数据99w订单量
        // controller -- service  ---- rpc
        // 导致一次查询 10W+的数据

        // 或者 请求过多（线程、资源)

    }
}
