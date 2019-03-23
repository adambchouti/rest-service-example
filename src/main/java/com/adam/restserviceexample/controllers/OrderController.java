package com.adam.restserviceexample.controllers;

import com.adam.restserviceexample.assemblers.OrderResourceAssembler;
import com.adam.restserviceexample.entities.Order;
import com.adam.restserviceexample.entities.Status;
import com.adam.restserviceexample.exceptions.OrderNotFoundException;
import com.adam.restserviceexample.repositories.OrderRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author adambchouti on 2019-03-23.
 * @project rest-service-example
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderResourceAssembler orderResourceAssembler;

    public OrderController(OrderRepository orderRepository,
                           OrderResourceAssembler orderResourceAssembler) {
        this.orderRepository = orderRepository;
        this.orderResourceAssembler = orderResourceAssembler;
    }

    @GetMapping
    public Resources<Resource<Order>> getAllOrders() {
        List<Resource<Order>> orders = orderRepository.findAll()
                .stream()
                .map(orderResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(
                orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public Resource<Order> getOrderById(@PathVariable Long id) {
        return orderResourceAssembler.toResource(
                orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id))
        );
    }

    @PostMapping
    public ResponseEntity<?> addNewOrder(@RequestBody Order order) {
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).getOrderById(newOrder.getId())).toUri())
                .body(orderResourceAssembler.toResource(newOrder));
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(orderResourceAssembler.toResource(orderRepository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(orderResourceAssembler.toResource(orderRepository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete an order that is in the " + order.getStatus() + " status"));
    }
}
