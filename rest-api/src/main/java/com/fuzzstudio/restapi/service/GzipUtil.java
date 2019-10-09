package com.fuzzstudio.restapi.service;

import com.fuzzstudio.restapi.entity.Customer;
import com.fuzzstudio.restapi.entity.Product;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

@Component
public class GzipUtil {

    public byte[] compress(Product product) throws Exception {
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(product.toString().getBytes(StandardCharsets.UTF_8));
        gzip.flush();
        gzip.close();
        return obj.toByteArray();
    }
}
