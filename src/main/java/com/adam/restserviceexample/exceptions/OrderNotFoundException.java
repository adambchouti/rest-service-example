package com.adam.restserviceexample.exceptions;

/**
 * @author adambchouti on 2019-03-23.
 * @project rest-service-example
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find order: " + id);
    }
}
