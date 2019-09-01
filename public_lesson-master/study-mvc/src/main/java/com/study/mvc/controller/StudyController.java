package com.study.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.mvc.annotation.Controller;
import com.study.mvc.annotation.Qualifier;
import com.study.mvc.annotation.RequestMapping;
import com.study.mvc.annotation.RequestParam;
import com.study.mvc.service.StudyService;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Qualifier("studyService")
	private StudyService studyService;
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param) {
		System.out.println(param);
		
		// 尝试调用service中方法
		studyService.insert(null);
		studyService.delete(null);
		studyService.update(null);
		studyService.select(null);
		
		try {
			response.getWriter().write("response.getWriter().write() \n");
			response.getWriter().write("doTest method success! param:" + param +"\n\n\n");

			response.getWriter().println("response.getWriter().println()");
			response.getWriter().println("doTest method success! param:" + param);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@RequestMapping("/hello")
	
	// @ResponseBody // 课后作业，
	public String test() {
		return "study.163.com";
	}
}
