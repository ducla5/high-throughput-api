package com.fuzzstudio.restapi;

import com.fuzzstudio.restapi.processor.InvalidateCacheProcessor;
import com.fuzzstudio.restapi.repo.ProductRepository;
import com.fuzzstudio.restapi.service.MockDataUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.fuzzstudio.restapi.repo.CustomerRepository;


@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories("com.fuzzstudio.restapi.repo")
public class RestApiApplication implements CommandLineRunner {

	public static final String topicExchangeName = "spring-boot-exchange";

	public static final String queueName = "spring-boot";


	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}


//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//									   MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames(queueName);
//		container.setMessageListener(listenerAdapter);
//		return container;
//	}

	@Bean
	MessageListenerAdapter listenerAdapter(InvalidateCacheProcessor processor) {
		return new MessageListenerAdapter(processor, "receiveMessage");
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final var rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}



	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MockDataUtil mockDataUtil;


	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mockDataUtil.createCategory();
		mockDataUtil.createProducts();

	}
}
