package com.zj.controller;

import com.zj.dao.EmployeeDao;
import com.zj.domainmodel.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao empDao;

    @GetMapping("/emps")
    public String findAllEmployees(Model model){
        Collection<Employee> employees = empDao.getAll();
        model.addAttribute("emps",employees);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String add(){
        return "emp/add";
    }
}
