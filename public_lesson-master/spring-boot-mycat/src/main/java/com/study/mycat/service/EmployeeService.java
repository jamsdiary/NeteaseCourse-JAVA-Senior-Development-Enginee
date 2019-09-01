package com.study.mycat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.mycat.mapper.EmployeeMapper;
import com.study.mycat.model.Employee;
import com.github.pagehelper.PageHelper;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getAll(Employee employee) {
        if (employee.getPage() != null && employee.getRows() != null) {
            PageHelper.startPage(employee.getPage(), employee.getRows());
        }
        return employeeMapper.selectByExample(employee);
    }

    public List<Employee> getAllByWeekend(Employee employee) {
        if (employee.getPage() != null && employee.getRows() != null) {
            PageHelper.startPage(employee.getPage(), employee.getRows());
        }
        return employeeMapper.selectByExample(employee);
    }

    public Employee getById(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
    	employeeMapper.deleteByPrimaryKey(id);
    }

    public void save(Employee employee) {
        if (employee.getIsNew() != "N" && !"".equals(employee.getIsNew())) {
        	employeeMapper.updateByPrimaryKey(employee);
        } else {
        	employeeMapper.insert(employee);
        }
    }
}
