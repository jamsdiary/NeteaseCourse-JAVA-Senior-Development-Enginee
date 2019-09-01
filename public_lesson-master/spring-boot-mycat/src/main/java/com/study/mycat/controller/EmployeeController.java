package com.study.mycat.controller;

import java.util.List;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.mycat.model.Employee;
import com.study.mycat.service.EmployeeService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping
    public ModelAndView getAll(Employee employee) {
        ModelAndView result = new ModelAndView("index_employee");
        List<Employee> employeeList = employeeService.getAllByWeekend(employee);
//        System.out.println("使用redis存点数据");
//        redisTemplate.opsForList().leftPush("empList", employeeList);
//        List<String> resultList = (List<String>)redisTemplate.opsForList().leftPop("empList");
//        System.out.println("我再取出来看下：" + resultList);
        result.addObject("pageInfo", new PageInfo<Employee>(employeeList));
        result.addObject("queryParam", employee);
        result.addObject("page", employee.getPage());
        result.addObject("rows", employee.getRows());
        //int a = 1/0;
        return result;
    }

    @Trace
    @ResponseBody
    @RequestMapping("sayhello")
    public String sayhello() {
        logger.info("sayhello .....");
        //TraceContext.traceId() API，在应用程序的任何地方获取traceId.
        System.out.println("========"+ TraceContext.traceId());
        ActiveSpan.tag("sayhello_tag", "sayhello .....");

        return "电商项目实战......";
    }

    @RequestMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView result = new ModelAndView("view_employee");
        result.addObject("employee", new Employee());
        return result;
    }

    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("view_employee");
        Employee employee = employeeService.getById(id);
        employee.setIsNew("N");
        result.addObject("employee", employee);
        return result;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, RedirectAttributes ra) {
        ModelAndView result = new ModelAndView("redirect:/employee");
        employeeService.deleteById(id);
        ra.addFlashAttribute("msg", "删除成功!");
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save1(Employee employee) {
        ModelAndView result = new ModelAndView("view_employee");
        String msg = employee.getId() == null ? "新增成功!" : "更新成功!";
        employeeService.save(employee);
        result.addObject("employee", employee);
        result.addObject("msg", msg);
        return result;
    }
}
