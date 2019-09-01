package com.study.architecture.web;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@RequestMapping("/user")
public class UserController {
    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("12345678");
    
    // 增加一个用户登录校验的逻辑
    @RequestMapping("/getToken")
    public String login(String userName, String passWord) {
    	//  TODO 课程效果忽略 .... 查数据库
    	if("tony".equals(userName) && "123".equals(passWord)) {
    		// 这里 不是去操作session。  是生成一个token并且返回
    		DefaultClaims info = new DefaultClaims();
    		info.put("userName", userName);
    		info.setId(UUID.randomUUID().toString());
    		// 设置有效期，设置其他权限信息  token.setExpiration(exp)
    		String token = jwtTokenProvider.createToken(info);
    		return token;
    	}
    	return "";
    }
    
    // 重新获取token的接口
    // refreshToken
    
	/**
	 * 获取用户相关的数据
	 * 
	 * @return 返回结果
	 */
	@ResponseBody
	@RequestMapping("/listData")
	public Object list(@RequestHeader("jwt-token")String token) {
		try {
			// 校验 是否登录。  生产环境：filter、aop、拦截器
			Claims info = jwtTokenProvider.parseToken(token);
			System.out.println("收到请求：" + info.get("userName").toString());
			
			// TODO 忽略相关的调用相关的接口或者service进行数据查询
			// TODO xxService.dosomething()
			return "success !" + System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
			return "error:" + e.getMessage();
		}
	}
}
