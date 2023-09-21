package com.project.micro.customer.service.impl;



import com.project.micro.customer.model.Customer;
import com.project.micro.customer.repo.ICustomerRepo;
import com.project.micro.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepo repo;

    @Override
    public Mono<Customer> save(Customer customer) {
        return repo.save(customer);
    }

    @Override
    public Mono<Customer> update(Customer customer) {
        return repo.save(customer);
    }

    @Override
    public Flux<Customer> findAll() {
        return repo.findAll();
    }

    @Override
    public Mono<Customer> findByid(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }
}
