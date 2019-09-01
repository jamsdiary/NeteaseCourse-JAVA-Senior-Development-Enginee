package com.study.session.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 演示session问题
 * @author allen
 */
@Controller
@RequestMapping("user")
public class LoginController {
	
	// 返回登录页面
	@RequestMapping("login")
	public String login(String type) {
		return "login";
	}

	// 展示首页
	@RequestMapping("doLogin")
	public String doLogin(String userName, String password, HttpServletRequest req) {
		HttpSession httpSession = req.getSession();
		String sessionUserName = (String) httpSession.getAttribute("userName");
		// 判断是否已经登录，已登录的直接返回首页
		if (!StringUtils.isEmpty(sessionUserName)) {
			return "main";
		}
		// 传入的用户名如果为空，返回登录页面
		if (StringUtils.isEmpty(userName)) {
			return "login";
		}
		// 将用户名写入缓存中
		httpSession.setAttribute("userName", userName);
		// 将创建该session的服务器端口写入缓存中
		httpSession.setAttribute("sessionPort", req.getLocalPort());
		return "main";
	}

	@RequestMapping("logout")
	// 登出后，返回登录页面
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "login";
	}

}
