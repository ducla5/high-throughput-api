package com.fuzzstudio.restapi.repo;

import com.fuzzstudio.restapi.entity.Category;
import com.fuzzstudio.restapi.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findById(String Id);
}