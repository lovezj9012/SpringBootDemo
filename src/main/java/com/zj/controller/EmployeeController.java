package com.zj.controller;

import com.zj.dao.DepartmentDao;
import com.zj.dao.EmployeeDao;
import com.zj.domainmodel.Department;
import com.zj.domainmodel.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao empDao;

    @Autowired
    private DepartmentDao depDao;

    @GetMapping("/emps")
    public String findAllEmployees(Model model) {
        Collection<Employee> employees = empDao.getAll();
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String add(Model model) {
        Collection<Department> depts = depDao.getDeparments();
        model.addAttribute("depts", depts);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee emp){
        System.out.println("保存的员工：" + emp);
        empDao.save(emp);
        //redirect: 表示重定向到一个地址 /代表当前项目路径
        //forward:  表示转发到一个地址
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String editToEmp(@PathVariable("id") Integer id,Model model){
        Employee employee = empDao.getEmployee(id);
        model.addAttribute("emp",employee);

        Collection<Department> deparments = depDao.getDeparments();
        model.addAttribute("depts",deparments);

        return "emp/add";
    }

    @PutMapping("/emp")
    public String updateEmp(Employee emp){
        empDao.save(emp);
        return "redirect:/emps";
    }

    @DeleteMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id){
        empDao.deleteEmp(id);
        return "redirect:/emps";
    }
}
