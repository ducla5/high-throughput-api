package com.fuzzstudio.restapi.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.fuzzstudio.restapi.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);
    Customer findCustomerById(String id);

}