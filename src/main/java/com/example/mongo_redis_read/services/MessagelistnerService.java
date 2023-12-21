package com.example.mongo_redis_read.services;

import java.io.IOException;
import java.util.List;

import com.example.mongo_redis_read.entity.StudentRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class MessagelistnerService implements MessageListener{

	@Autowired
	private ObjectMapper objectMapper;
	private ApplicationContext applicationContext;
	private StudentDataRedisService studentDataRedisService;


	@Override
	public void onMessage(Message message, byte[] pattern) {
		StudentRedis data=null;
		applicationContext=ApplicationContextGet.getApplicationContext();
		studentDataRedisService=applicationContext.getBean(StudentDataRedisService.class);
		try {
			data = objectMapper.readValue(message.getBody(), StudentRedis.class);
			if(data!=null){
				List<StudentRedis> studentRedis=studentDataRedisService.getAllStudentRedisData();
				studentRedis.add(data);
				studentDataRedisService.refresh(studentRedis);
			}
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(data);
	}

}
