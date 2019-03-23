package com.adam.restserviceexample.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.adam.restserviceexample.assemblers.EmployeeResourceAssembler;
import com.adam.restserviceexample.entities.Employee;
import com.adam.restserviceexample.exceptions.EmployeeNotFoundException;
import com.adam.restserviceexample.repositories.EmployeeRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adambchouti on 2019-03-20.
 * @project rest-service-example
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private final EmployeeResourceAssembler employeeResourceAssembler;

    public EmployeeController(
            EmployeeRepository employeeRepository,
            EmployeeResourceAssembler employeeResourceAssembler) {
        this.employeeRepository = employeeRepository;
        this.employeeResourceAssembler = employeeResourceAssembler;
    }

    @GetMapping
    public Resources<Resource<Employee>> allEmployees() {
        List<Resource<Employee>> employees = employeeRepository.findAll().stream()
                .map(employeeResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(
                employees,
                linkTo(methodOn(EmployeeController.class).allEmployees()).withSelfRel()
        );
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {
        Resource<Employee> resource = employeeResourceAssembler.toResource(employeeRepository.save(newEmployee));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/{id}")
    public Resource<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeResourceAssembler.toResource(employee);
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
