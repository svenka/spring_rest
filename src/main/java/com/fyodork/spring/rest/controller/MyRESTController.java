package com.fyodork.spring.rest.controller;

import com.fyodork.spring.rest.entity.Employee;
import com.fyodork.spring.rest.exception_handling.EmployeeIncorrectData;
import com.fyodork.spring.rest.exception_handling.NoSuchEmployeeException;
import com.fyodork.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee>showAllEmployees()
    {
        List<Employee> allEmployees=employeeService.getAllEmployees();
        return allEmployees;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id)
    {
        Employee employee=employeeService.getEmployee(id);

        if(employee==null)
        {
            throw  new NoSuchEmployeeException("Не найден Сотрудник с id="+id+" в БД");
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee employee)
    {
        employeeService.saveEmployee(employee);
        return employee;

    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee)
    {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id)
    {
        Employee employee=employeeService.getEmployee(id);
        if(employee==null)
        {
            throw new NoSuchEmployeeException("there is no employee with ID="
                    +id+" in database");

        }
        else
        employeeService.deleteEmployee(id);
        return "employee with id="+id+" was deleted";
    }




}
