package com.adam.restserviceexample.exceptions;

/**
 * @author adambchouti on 2019-03-20.
 * @project rest-service-example
 */
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Could not find employee: " + id);
    }
}
