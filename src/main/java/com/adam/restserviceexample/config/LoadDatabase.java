package com.adam.restserviceexample.config;

import com.adam.restserviceexample.entities.Employee;
import com.adam.restserviceexample.entities.Order;
import com.adam.restserviceexample.entities.Status;
import com.adam.restserviceexample.repositories.EmployeeRepository;
import com.adam.restserviceexample.repositories.OrderRepository;
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
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository,
                                   OrderRepository orderRepository) {
        return args -> {
            log.info("Preloading: " + employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar")));
            log.info("Preloading: " + employeeRepository.save(new Employee("Frodo", "Baggins", "thief")));

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
