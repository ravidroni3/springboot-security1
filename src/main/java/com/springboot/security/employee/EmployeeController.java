package com.springboot.security.employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private static final List<Employee> employees = Arrays.asList(
            new Employee (1,"ravi"),
            new Employee(2,"raj"),
            new Employee(3,"ram")
    );

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") Integer  employeeId){
        return employees.stream()
                .filter(emp -> employeeId.equals(emp.getEmployeeId()))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("Employee" + employeeId + "does not exist"));

    }
}
