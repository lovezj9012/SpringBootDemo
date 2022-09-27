package com.zj.dao;

import com.zj.domainmodel.Department;
import com.zj.domainmodel.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EmployeeDao {

    private static Map<Integer, Employee> employees = null;

    @Autowired
    private DepartmentDao departmentDao;

    static {
        employees = new HashMap<>();
        employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1, new Department(101, "D-AA"), new Date()));
        employees.put(1002, new Employee(1002, "E-BB", "ab@163.com", 1, new Department(102, "D-BB"), new Date()));
        employees.put(1003, new Employee(1003, "E-CC", "ac@163.com", 0, new Department(103, "D-CC"), new Date()));
        employees.put(1004, new Employee(1004, "E-DD", "ad@163.com", 0, new Department(104, "D-DD"), new Date()));
        employees.put(1005, new Employee(1005, "E-EE", "ae@163.com", 1, new Department(105, "D-EE"), new Date()));
    }

    private static  Integer initId =1006;

    public void save(Employee employee){
        if(employee.getId()==null){
            employee.setId(initId++);
        }
    }

    public Collection<Employee> getAll(){
        return employees.values();
    }
}
