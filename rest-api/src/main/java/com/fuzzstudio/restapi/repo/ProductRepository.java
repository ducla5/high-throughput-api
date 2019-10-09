package com.fuzzstudio.restapi.repo;

import com.fuzzstudio.restapi.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findById(String Id);
    List<Product> findByCategoryId(String categoryId);

}