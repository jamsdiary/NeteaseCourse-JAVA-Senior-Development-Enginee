package com.study.redis.lock.miaosha.web;

import com.study.redis.lock.miaosha.service.MiaoshaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 秒杀页面访问接口
 */
@Controller
public class MiaoshaController {
	@Autowired
	MiaoshaService miaoshaService;

	/** 跳转首页 */
	@RequestMapping("/")
	public String index(ModelMap model, HttpServletRequest request) {
		String message = "当前服务器端口：" + request.getLocalAddr() + ":" + request.getLocalPort();
		model.put("message", message);
		return "home";
	}

	/** 秒杀接口 */
	@RequestMapping("/miaosha")
	@ResponseBody
	public Object getUserInfo(String goodsCode, String userId) throws Exception {
		// http请求，后台就是一个thread线程 去 调用service方法
		return miaoshaService.miaosha(goodsCode, userId);
	}
}
