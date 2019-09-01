package com.study.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.study.mybatis.entity.User;
import com.study.mybatis.service.UserService;
import com.study.mybatis.utils.PageInfo;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 借助数组进行分页
	 * 
	 * @param pageNo 第几页
	 * @param pageSize 每页多少条
	 * @return ModelAndView
	 */
	@RequestMapping("array/{pageNo}/{pageSize}")
	public ModelAndView getUserByArray(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
		ModelAndView mv = new ModelAndView("user/list");
		List<User> users = userService.selectUsersByArray(pageNo, pageSize);
		mv.addObject("users", users);
		return mv;
	}

	/**
	 * 基于sql语句的分页实现
	 * 
	 * @param pageNo 第几页
	 * @param pageSize 每页多少条
	 * @return ModelAndView
	 */
	@RequestMapping("sql/{pageNo}/{pageSize}")
	public ModelAndView getUserBySql(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
		ModelAndView mv = new ModelAndView("user/list");
		List<User> users = userService.selectUsersBySql(pageNo, pageSize);
		mv.addObject("users", users);
		return mv;
	}

	/**
	 * 通过RowBounds参数进行物理分页
	 * 
	 * @param start 开始位置
	 * @param limit 多少条
	 * @return ModelAndView
	 */
	@RequestMapping("page/{start}/{limit}")
	public ModelAndView getUserByRowBounds(@PathVariable("start") int start, @PathVariable("limit") int limit) {
		ModelAndView mv = new ModelAndView("user/list");
		List<User> users = userService.selectUsersByRowBounds(start, limit);
		mv.addObject("users", users);
		return mv;
	}

	/**
	 * 通过拦截器进行物理分页
	 */
	@RequestMapping("page")
	public ModelAndView getUserByPage(HttpServletRequest request, HttpServletResponse response) {
		int currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));

		String pageStr = String.format("<a href=\"%s\">上一页</a>    <a href=\"%s\">下一页</a>",
				request.getRequestURI() + "?page=" + (currentPage - 1),
				request.getRequestURI() + "?page=" + (currentPage + 1));

		ModelAndView mv = new ModelAndView("user/pagelist");
		PageInfo page = new PageInfo();
		page.setCurrentPage(currentPage);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		List<User> users = userService.selectUsersByPage(params);
		mv.addObject("users", users);
		mv.addObject("pageStr", pageStr);
		return mv;
	}

}
