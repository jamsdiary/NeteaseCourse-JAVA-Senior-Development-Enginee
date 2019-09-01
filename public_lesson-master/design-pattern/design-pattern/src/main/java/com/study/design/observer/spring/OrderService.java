package com.study.design.observer.spring;

import org.springframework.stereotype.Service;

/** 订单操作，业务伪代码 */
@Service
public class OrderService {
  /** 新订单订单 */
  public void saveOrder() {
    // 伪代码的意思：忽略很多逻辑，参数封装、数据查询等等逻辑
    // 1---创建订单---
    System.out.println("1、 订单创建成功");
    // 2---短信通知
    System.out.println("2、 调用短信发送的接口 -> 恭喜喜提羽绒被子");
  }
}
