package com.example.mongo_redis_read.repositories;

import org.springframework.stereotype.Component;

@Component
public class RedisConstants {
	
	public static final String STUDENT_KEY_PATTERN="STUDENT_TABLE_REDIS_HASH*";
	public static final  String STUDENT_KEY_PATTERN_IGNORE="STUDENT_TABLE_REDIS_HASH";
}
