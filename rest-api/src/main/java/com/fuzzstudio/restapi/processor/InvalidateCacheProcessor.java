package com.fuzzstudio.restapi.processor;


import com.fuzzstudio.restapi.RestApiApplication;
import com.fuzzstudio.restapi.enums.Command;
import com.fuzzstudio.restapi.message.StreamMessage;
import com.fuzzstudio.restapi.service.CacheUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Component
public class InvalidateCacheProcessor {

    private CountDownLatch latch = new CountDownLatch(1);

    private static final Logger LOGGER = Logger.getLogger(InvalidateCacheProcessor.class.getName());

    @Autowired
    CacheUtil cacheUtil;

    @RabbitListener(queues = RestApiApplication.queueName)
    public void receiveMessage(StreamMessage message) {

        if(message.command == Command.INVALIDATE_ALL) {
            cacheUtil.getCache().invalidateAll();
            LOGGER.info("Invalidate all product");
        } else if(message.command == Command.INVALIDATE_ID) {
            cacheUtil.getCache().invalidate(message.id);
            LOGGER.info("invalidate product with id: " + message.id);
        } else {
            LOGGER.info("Receive: ");
            LOGGER.info(message.command.toString());
        }

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
