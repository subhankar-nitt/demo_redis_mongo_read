package com.example.mongo_redis_read.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.mongo_redis_read.entity.StudentKey;


@Component
public class StudentRedisKeyToStringConverter implements Converter<String,StudentKey> {

	@Override
	public StudentKey convert(String source) {
		String [] array = source.split(":");
		StudentKey studentkey= new StudentKey();
		studentkey.setId(array[0]);
		studentkey.setName(array[1]);
		studentkey.setClassName(array[2]);
		
		return studentkey;
	}

	

}
