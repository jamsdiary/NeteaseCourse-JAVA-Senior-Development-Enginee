package com.study.order.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private String id;

    private Date orderTime;

    private String phone;

    private Integer state;

    private Double total;

    private Integer userId;

}