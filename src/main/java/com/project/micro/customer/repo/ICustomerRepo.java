package com.project.micro.customer.repo;

import com.project.micro.customer.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ICustomerRepo extends ReactiveMongoRepository<Customer,String> {
}
