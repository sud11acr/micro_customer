package com.project.micro.customer.service;

import com.project.micro.customer.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Mono<Customer> save(Customer customer);
    Mono<Customer> update(Customer customer);
    Flux<Customer> findAll();
    Mono<Customer>findByid(String id);
    Mono<Void> delete(String id);
}
