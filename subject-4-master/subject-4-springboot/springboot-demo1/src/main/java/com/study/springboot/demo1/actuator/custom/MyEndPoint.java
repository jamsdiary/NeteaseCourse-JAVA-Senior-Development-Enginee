package com.study.springboot.demo1.actuator.custom;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

@Endpoint(id = "myEndpoint")
@Component
public class MyEndPoint {

    String name = "default";

    @ReadOperation
    public String getName() {
        // spring http端点的json格式
        return "{\"name\":\""+name+"\"}";
    }

    @DeleteOperation
    public void delName() {
        name = "";
    }

    @WriteOperation
    public void setName(@Selector String name) {
        this.name = name;
    }

}

