package com.example.mongo_redis_read.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface StudentDataRedisRepository {
	public Map<String,Map<String,String>> getAlldata(List<String> listHashKeys) ;
	public List<String> getAllHashKeys(String strPattern,String strPatternIgnr );
}
