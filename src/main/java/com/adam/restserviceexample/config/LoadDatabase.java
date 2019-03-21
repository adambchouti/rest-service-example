package com.adam.restserviceexample.config;

import com.adam.restserviceexample.entities.Employee;
import com.adam.restserviceexample.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author adambchouti on 2019-03-20.
 * @project rest-service-example
 */
@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
        return args -> {
            log.info("Preloading: " + employeeRepository.save(new Employee("Bilbo Baggins", "burglar")));
            log.info("Preloading: " + employeeRepository.save(new Employee("Frodo Baggins", "thief")));
        };
    }
}
