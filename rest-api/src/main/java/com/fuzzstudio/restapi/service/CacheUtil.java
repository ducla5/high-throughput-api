package com.fuzzstudio.restapi.service;

import com.fuzzstudio.restapi.entity.Product;
import com.fuzzstudio.restapi.repo.CustomerRepository;
import com.fuzzstudio.restapi.repo.ProductRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheUtil {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GzipUtil gzipUtil;

    private CacheLoader<String, byte[]> loader;
    private LoadingCache<String, byte[]> cache;

    public CacheUtil() {

        loader = new CacheLoader<>() {
            @Override
            public byte[] load(String s) throws Exception {
                return gzipUtil.compress(productRepository.findById(s).orElse(new Product()));
            }
        };

        cache = CacheBuilder.newBuilder().build(loader);
    }

    public LoadingCache getCache() {
        return cache;
    }

}
