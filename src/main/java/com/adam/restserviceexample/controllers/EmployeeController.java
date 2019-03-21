package com.adam.restserviceexample.controllers;

import com.adam.restserviceexample.entities.Employee;
import com.adam.restserviceexample.exceptions.EmployeeNotFoundException;
import com.adam.restserviceexample.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author adambchouti on 2019-03-20.
 * @project rest-service-example
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> allEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Employee replaceEmployee(@RequestBody Employee newEmployee,
                                    @PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                   employee.setName(newEmployee.getName());
                   employee.setRole(newEmployee.getRole());
                   return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }
}
