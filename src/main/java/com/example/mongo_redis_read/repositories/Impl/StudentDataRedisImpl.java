package com.example.mongo_redis_read.repositories.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.mongo_redis_read.repositories.StudentDataRedisRepository;
import redis.clients.jedis.*;

public class StudentDataRedisImpl implements StudentDataRedisRepository{

	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public Map<String,Map<String,String>> getAlldata(List<String> listHashKeys) {
		
		Map<String,Map<String,String>> data = null;
		String strPattern =  listHashKeys!=null && listHashKeys.size()>0?listHashKeys.get(0):null;
		
		try(Jedis jedis =  jedisPool.getResource()){
//			List<String> colNames = getAllColumnNames(strPattern,jedis);
			data= new HashMap<>();
			for(String hashKeys: listHashKeys) {
				data.put(hashKeys, jedis.hgetAll(hashKeys));
			}
		}catch(Exception  ex) {
			System.err.println(ex);
		}
		return data;
	}
	
	private List<String> getAllColumnNames(String strPattern,Jedis jedis){
		List<String>  colNames = new ArrayList<>();
		Set<String> hkeysSet = jedis.hkeys(strPattern);
		for (String hkey: hkeysSet) {
			if(hkey.equals("_class"))
				continue;
			colNames.add(hkey);
		}
		return colNames;
	};
	@Override
	public List<String> getAllHashKeys(String strPattern,String strPatternIgnr ){
		List<String> hashKeys=new ArrayList<>();
		Jedis jedis = jedisPool.getResource();
		ScanParams scanParams =  new ScanParams();
		scanParams.match(strPattern);
		String cursor= scanParams.SCAN_POINTER_START;
		
		do {
			ScanResult<String> scanResults= jedis.scan(cursor,scanParams);
			for(String key: scanResults.getResult()) {
				if(key.matches(strPatternIgnr)) {
					continue;
				}
				if(!hashKeys.contains(key)) {
					hashKeys.add(key);
				}
			}
			cursor=scanResults.getCursor();
		}while(!cursor.equals(cursor.valueOf(0)));
		
		return hashKeys;
	}
}
