package com.zj.controller;

import com.zj.domainmodel.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {

    @GetMapping("/emps")
    public List<Employee> findAllEmployees(){

    }
}
