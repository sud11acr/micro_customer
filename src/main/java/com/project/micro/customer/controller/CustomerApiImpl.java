package com.project.micro.customer.controller;


import com.project.micro.customer.api.CustomerApi;

import com.project.micro.customer.dto.CustomerRequest;
import com.project.micro.customer.model.Customer;
import com.project.micro.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class CustomerApiImpl implements CustomerApi{

    @Autowired
    private ICustomerService service;

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String id, ServerWebExchange exchange) {
        return service.findByid(id)
                .flatMap(p->service.delete(p.getIdCustomer())
                        .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerRequest>>> findAllCustomer(ServerWebExchange exchange) {
        Flux<CustomerRequest> fx=service.findAll().map(c->{
            return getCustomerRequest(c);
        });
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx));
    }

    @Override
    public Mono<ResponseEntity<CustomerRequest>> findByIdCustomer(String id, ServerWebExchange exchange) {
        Mono<CustomerRequest> mono=service.findByid(id).map(c->{
            return getCustomerRequest(c);
        });
        return mono
                .map(p->ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p));
    }



    @Override
    public Mono<ResponseEntity<CustomerRequest>> saveCustomer(Mono<CustomerRequest> customer, ServerWebExchange exchange) {
        return  customer.map(p->{
                    return getCustomer(p);
                }).flatMap(c->service.save(c))
                .map(c->{
                    return getCustomerRequest(c);
                })
                .map(p->ResponseEntity.created(URI.create("Localhost:8987".concat("/").concat(p.getIdCustomer())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p));

    }


    @Override
    public Mono<ResponseEntity<CustomerRequest>> updateConsumer(String id, Mono<CustomerRequest> customer, ServerWebExchange exchange) {
        Mono<Customer> monoBody=customer.map(p->{
            return getCustomer(p);
        });
        Mono<Customer> monoBD=service.findByid(id);

        return monoBD.zipWith(monoBody,(bd,pl)->{
                    bd.setIdCustomer(id);
                    bd.setName(pl.getName());
                    bd.setCustomerKind(pl.getCustomerKind());
                    bd.setDocument(pl.getDocument());
                    bd.setLastName(pl.getLastName());
                    bd.setModificationDate(new Date());
                    bd.setStatus(pl.getStatus());
                    return bd;
                })
                .flatMap(p->service.update(p))
                .map(c->{
                    return getCustomerRequest(c);
                })
                .map(y->ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(y))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    private static Customer getCustomer(CustomerRequest p) {
        Customer customerBd=new Customer();
        customerBd.setIdCustomer(p.getIdCustomer());
        customerBd.setCustomerKind(p.getCustomerKind());
        customerBd.setName(p.getName());
        customerBd.setLastName(p.getLastName());
        customerBd.setStatus(true);
        customerBd.setDocument(p.getDocument());
        customerBd.setRegistrationDate(new Date());
        customerBd.setModificationDate(new Date());
        return customerBd;
    }
    private static CustomerRequest getCustomerRequest(Customer c) {
        CustomerRequest customerRequest=new CustomerRequest();
        customerRequest.setIdCustomer(c.getIdCustomer());
        customerRequest.setCustomerKind(c.getCustomerKind());
        customerRequest.setDocument(c.getDocument());
        customerRequest.setName(c.getName());
        customerRequest.setLastName(c.getLastName());
        customerRequest.setStatus(c.getStatus());
        return customerRequest;
    }
}
