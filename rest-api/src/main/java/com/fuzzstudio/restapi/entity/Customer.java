package com.fuzzstudio.restapi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;
import javax.persistence.Entity;

@Entity
public class Customer {

    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String description;

    public Customer() {}

    public Customer(String firstName, String lastName, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "{\"id\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"description\": \"%s\"}",
                id, firstName, lastName, description);
    }

}