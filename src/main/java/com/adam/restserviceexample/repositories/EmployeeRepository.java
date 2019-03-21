package com.adam.restserviceexample.repositories;

import com.adam.restserviceexample.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adambchouti on 2019-03-20.
 * @project rest-service-example
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
