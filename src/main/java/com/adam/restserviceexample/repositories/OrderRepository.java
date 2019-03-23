package com.adam.restserviceexample.repositories;

import com.adam.restserviceexample.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adambchouti on 2019-03-23.
 * @project rest-service-example
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
