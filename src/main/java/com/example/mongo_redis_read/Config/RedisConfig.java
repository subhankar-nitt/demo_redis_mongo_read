package com.example.mongo_redis_read.Config;

import com.example.mongo_redis_read.services.MessagelistnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.clients.jedis.JedisPool;

import com.example.mongo_redis_read.entity.Student;
import com.example.mongo_redis_read.entity.StudentKey;
import com.example.mongo_redis_read.entity.StudentRedis;
import com.example.mongo_redis_read.repositories.StudentDataRedisRepository;
import com.example.mongo_redis_read.repositories.StudentDataRepository;
import com.example.mongo_redis_read.repositories.Impl.StudentDataRedisImpl;
import com.example.mongo_redis_read.repositories.Impl.StudentDataRepositoryImpl;


@Configuration
@ComponentScan(basePackages = {"com.example.mongo_redis_read.repositories"})
@EnableRedisRepositories(basePackages = {"com.example.mongo_redis_read.repositories"})
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;
	
	@Value("${spring.data.redis.port}")
	private int port;
	@Value("${redis.pubsub.topic:channel-events}")
	private String topic;

	@Autowired
	private MessagelistnerService messagelistnerService;
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		
		RedisStandaloneConfiguration redisStandaloneConfiguration=new  RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
//		JedisClientConfiguration conf = JedisClientConfiguration.builder().useSsl().build();
		JedisConnectionFactory factory= new JedisConnectionFactory(redisStandaloneConfiguration);
		return factory;
	}
	
	@Bean
	public RedisTemplate<StudentKey,StudentRedis>redisTemplate(){
		
		RedisTemplate<StudentKey, StudentRedis> redisTemplate=  new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}
	
	@Bean
	public JedisPool jedisPool() {
		JedisPool jedisPool= new JedisPool(host=host,port=port);
		return jedisPool;
	}
	
	@Bean
	public StudentDataRedisRepository redisrepository() {
		return new StudentDataRedisImpl();
	}
	
	@Bean
	public StudentDataRepository studentDataRepo() {
		return new StudentDataRepositoryImpl();
	}
	@Bean
	public MessageListenerAdapter messageListener() {
	    return new MessageListenerAdapter(messagelistnerService);
	}

	@Bean
	public RedisMessageListenerContainer redisContainer() {
	    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	    container.setConnectionFactory(jedisConnectionFactory());
	    container.addMessageListener(messageListener(), topic());
	    return container;
	}
	@Bean
	public ChannelTopic topic() {
		return new ChannelTopic(topic);
	}

}
