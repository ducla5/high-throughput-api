package com.fuzzstudio.restapi.service;

import com.fuzzstudio.restapi.entity.Category;
import com.fuzzstudio.restapi.entity.Product;
import com.fuzzstudio.restapi.repo.CategoryRepository;
import com.fuzzstudio.restapi.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class MockDataUtil {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public void createCategory() {

        categoryRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Category category = new Category(RandomString.getAlphaNumericString(32),RandomString.getAlphaNumericString(1024),RandomString.getAlphaNumericString(256));
            categoryRepository.save(category);
        }
    }

    public void createProducts() {

        productRepository.deleteAll();

        for (int i = 0; i < 1000; i++) {
            Random random = new Random();
            List<Category> categories = categoryRepository.findAll();
            int category = random.nextInt((int) categoryRepository.count());


            Map<String, String> map  = new HashMap<>();

            for (int j = 0; j < category; j++) {
                map.put(RandomString.getAlphaNumericString(32), RandomString.getAlphaNumericString(64));
            }

            Product product = new Product(categories.get(category),RandomString.getAlphaNumericString(1024),RandomString.getAlphaNumericString(256), map);

            productRepository.save(product);
        }

    }

}
