package com.study.sessdemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 演示本地tomcat sessin机制
 * 
 * @author allen
 */
@Controller
public class SessionController {

	@RequestMapping("/session")
	@ResponseBody
	public String sessionTrack(HttpServletRequest request) {
		
		//这段代码取消，是不会创建session
		//jsp 九大内置对象， session对象 jsp默认，
		HttpSession session = request.getSession();
		
		session.setAttribute("names", "Toney Mike Allen");
		
		return "曾经有一份真诚的爱情放在我面前，我没有珍惜，等我失去的时候我才后悔莫及，人世间最痛苦的事莫过于此。" +
				"如果上天能够给我一个再来一次的机会，我会对那个女孩子说三个字：我爱你。" +
				"如果非要在这份爱上加上一个期限，我希望是……一万年！";
		
	}
}
