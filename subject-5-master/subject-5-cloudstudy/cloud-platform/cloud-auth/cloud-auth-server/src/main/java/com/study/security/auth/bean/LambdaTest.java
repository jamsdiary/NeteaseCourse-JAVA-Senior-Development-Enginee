package com.study.security.auth.bean;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName LambdaTest
 * @Description lambda stream 例子
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/7/12 16:02
 * @Version 1.0
 */
public class LambdaTest {
    public static void main(String[] args) {
        String allIfs = "1997:0.667;1998:0.696;1999:0.738;2000:1.257;" +
                "2001:0.829;2002:0.75;2003:1.089;2004:0.614;2005:0.951;";
        //Map<String, Double> kvs =
        Arrays.asList(allIfs.split(";"))
                .stream()
                .map(em -> em.split(":"))
                .filter(em -> em.length ==2)
                .collect(Collectors.toMap(e -> e[0], e -> Double.parseDouble(e[1])))
                .forEach((k,v) -> {System.out.println(k+" = "+v);});

        // --------------------------------


    }
}
