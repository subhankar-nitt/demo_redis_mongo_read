package com.example.mongo_redis_read.Config;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.mongo_redis_read.entity.Student;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Configuration
@EnableMongoRepositories(basePackages = {"com.example.mongo_redis_read.repositories"})
public class MongoConfig {

	@Value("${spring.data.mongodb.username}")
	private String userName;
	
	@Value("${spring.data.mongodb.password}")
	private String password;
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
	@Value("${spring.data.mongdb.collection}")
	private String collection;
	
	@Bean
	public MongoClient getMongoClient() {
		final String connectionString = "mongodb+srv://"+userName+":"+password+"@cluster0.uni5bpo.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient mongoClient= MongoClients.create(settings);
        return mongoClient;
	}
	
	@Bean
	public MongoCollection<Student> getMongoCollection(){
		MongoClient mongoClient= getMongoClient();
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongoDatabase= mongoClient.getDatabase(database).withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Student> mongoCollection= mongoDatabase.getCollection(collection,Student.class);
//        mongoClient.close();
        return mongoCollection;
	}
}
