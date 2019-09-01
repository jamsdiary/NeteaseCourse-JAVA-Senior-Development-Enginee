package com.study.architecture.test;

import com.study.architecture.web.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * JWT-Token生成和校验方案
 * 
 * @author Tony
 *
 */
public class JWTtests {
    public static void main(String[] args) {
        // 密钥 12345678    Jwt  --  json web token
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("12345678");
        
        // 服务端校验通过之后， 生成一个token字符串。内容： 你根据业务需要填充
        DefaultClaims claims = new DefaultClaims();
        claims.put("userName", "Tony");
        claims.put("expire", "2018.11.14 00:00:00"); // token有效期

        String token = jwtTokenProvider.createToken(claims);
        System.out.println("生成的token：" + token);
        
        // 解析Token
        Claims userClaims = jwtTokenProvider.parseToken(token);
        System.out.println("解析出来的Toekn内容：" + userClaims);
    }
}
