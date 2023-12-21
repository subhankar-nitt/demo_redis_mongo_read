package com.example.mongo_redis_read;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan({"com.example.mongo_redis_read.repositories","com.example.mongo_redis_read.services"})
@EnableMongoRepositories({"com.example.mongo_redis_read.repositories"})
@SpringBootApplication
public class DemoRedisMongoReadApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRedisMongoReadApplication.class, args);
	}

}
