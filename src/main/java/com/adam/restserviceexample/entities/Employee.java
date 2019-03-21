package com.adam.restserviceexample.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author adambchouti on 2019-03-20.
 * @project rest-service-example
 */
@Data
@Entity
public class Employee {

    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }
}
