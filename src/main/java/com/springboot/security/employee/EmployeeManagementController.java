package com.springboot.security.employee;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeManagementController {

    private static final List<Employee> employees = Arrays.asList(
            new Employee(1, "ravi"),
            new Employee(2, "raj"),
            new Employee(3, "ram")
    );

    @GetMapping
    @PreAuthorize("hasRole('Role_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Employee> getAllEmployees() {
        System.out.println("getAllEmployees");
        return employees;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('employee:write')")
    public Employee registerNewEmployee(@RequestBody Employee employee) {
        System.out.println(employee);

        return employee;
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('employee:write')")
    public void updateEmployee(Integer employeeId, Employee employee){
        System.out.println(String.format("%s %s", employee, employee));

    }
    @DeleteMapping(path = "/{employeeId}")
    @PreAuthorize("hasAuthority('employee:write')")
    public void deleteEmployee(@PathVariable("employeeId") Integer employeeId){
        System.out.println(employeeId);
    }
}