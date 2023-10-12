package com.project.micro.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {
    @Id
    private String idCustomer;

    private String name;
    private String lastName;
    private String document;
    private String customerType;
    private Date registrationDate;
    private Date modificationDate;
    private Boolean status;


}
