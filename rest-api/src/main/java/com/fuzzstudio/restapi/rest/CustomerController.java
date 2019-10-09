package com.fuzzstudio.restapi.rest;

import com.fuzzstudio.restapi.entity.Customer;
import com.fuzzstudio.restapi.repo.CustomerRepository;
import com.fuzzstudio.restapi.service.CacheUtil;
import com.fuzzstudio.restapi.service.GzipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;

@RestController()
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private GzipUtil gzipUtil;

    @GetMapping("/cache/{first_name}")
    public byte[] getCustomer(@PathVariable("first_name") String first_name, HttpServletResponse response) {

        response.addHeader("content-encoding","gzip");
        response.addHeader("Content-Type","application/json");

        return (byte[]) cacheUtil.getCache().getUnchecked(first_name);
    }

    @GetMapping("/nocache/{first_name}")
    public Customer getCustomerNoCache(@PathVariable("first_name") String first_name) {
        return repository.findByFirstName(first_name);
    }
}
