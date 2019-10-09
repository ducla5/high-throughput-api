package com.fuzzstudio.restapi.rest;

import com.fuzzstudio.restapi.RestApiApplication;
import com.fuzzstudio.restapi.entity.Customer;
import com.fuzzstudio.restapi.entity.Product;
import com.fuzzstudio.restapi.enums.Command;
import com.fuzzstudio.restapi.message.StreamMessage;
import com.fuzzstudio.restapi.processor.InvalidateCacheProcessor;
import com.fuzzstudio.restapi.repo.CustomerRepository;
import com.fuzzstudio.restapi.repo.ProductRepository;
import com.fuzzstudio.restapi.service.CacheUtil;
import com.fuzzstudio.restapi.service.GzipUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController()
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private GzipUtil gzipUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private InvalidateCacheProcessor processor;

    private final static Logger logger = Logger.getLogger(ProductController.class.getName());

    @GetMapping("/cache/{id}")
    public ResponseEntity getCustomer(@PathVariable("id") String id, HttpServletResponse response) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("content-encoding","gzip")
                .contentType(MediaType.APPLICATION_JSON)
                .body((byte[]) cacheUtil.getCache().getUnchecked(id));

    }

    @GetMapping("/nocache/{id}")
    public Product getCustomerNoCache(@PathVariable("id") String id) {
        return repository.findById(id).orElse(new Product());
    }

    @GetMapping("/invalidate")
    public ResponseEntity invalidateCache() {


        System.out.println("Sending invalidate all");
        rabbitTemplate.convertAndSend(RestApiApplication.topicExchangeName, "foo.bar.baz", new StreamMessage(Command.INVALIDATE_ALL,""));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"Code\": \"OK\"}");
    }

    @GetMapping("/invalidate/{id}")
    public ResponseEntity invalidateProductCache(@PathVariable("id") String id) {

        System.out.println("Sending invalidate id: "+id);

        rabbitTemplate.convertAndSend(RestApiApplication.topicExchangeName, "foo.bar.baz", new StreamMessage(Command.INVALIDATE_ID, id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"Code\": \"OK\"}");
    }
}
