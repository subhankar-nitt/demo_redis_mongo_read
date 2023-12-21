package com.example.mongo_redis_read.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.mongo_redis_read.converters.StudentRedisKeyToStringConverter;
import com.example.mongo_redis_read.entity.Student;
import com.example.mongo_redis_read.entity.StudentEntity;
import com.example.mongo_redis_read.entity.StudentKey;
import com.example.mongo_redis_read.entity.StudentRedis;
import com.example.mongo_redis_read.repositories.RedisConstants;
import com.example.mongo_redis_read.repositories.StudentDataRedisRepository;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

@Service
public class StudentDataRedisService {

	
	@Autowired
	private StudentDataRedisRepository studentDataRedisRepository;
	
	@Autowired
	private  StudentRedisKeyToStringConverter studentRedisKeyToStringConverter;

	@Autowired
	private ApplicationContext appContext;

	private List<String> allHashKeys;
	private List<StudentRedis> allStudents;
	private String strPattern=RedisConstants.STUDENT_KEY_PATTERN;
	private String strPatternIgnr=RedisConstants.STUDENT_KEY_PATTERN_IGNORE;
	@PostConstruct
	public void createstudentList(){

		System.out.println("Calling Post construct method ..... ");
		appContext=ApplicationContextGet.getApplicationContext();
		studentDataRedisRepository=appContext.getBean(StudentDataRedisRepository.class);
		studentRedisKeyToStringConverter=appContext.getBean(StudentRedisKeyToStringConverter.class);
		allStudents=getAllStudentData();
		allHashKeys=getAllHashKeys(strPattern,strPatternIgnr);
	}

	public List<StudentRedis> getAllStudentRedisData(){
		return allStudents;
	}
	protected void refresh(List<StudentRedis> refreshedData){
		allStudents=refreshedData!=null && allStudents!=null && refreshedData.size()>=allStudents.size()?refreshedData
				:allStudents;
	}

	private List<StudentRedis> getAllStudentData(){
		allStudents=new ArrayList<>();
		allHashKeys=getAllHashKeys(strPattern,strPatternIgnr);
		List<Map.Entry<StudentKey,StudentEntity>> hmaps=getAllStudentData(allHashKeys);
		for (Map.Entry<StudentKey,StudentEntity> stdEntity : hmaps ) {
			StudentRedis studentRedis = convertToStudentRedis(stdEntity);
			allStudents.add(studentRedis);
		}
		return allStudents;
	}
	private StudentRedis convertToStudentRedis(Map.Entry<StudentKey,StudentEntity> map) {
		StudentRedis studentRedis = new StudentRedis();
		studentRedis.setStudentKey(map.getKey());
		
		StudentEntity entity=map.getValue();
		studentRedis.setId(entity.getId());
		studentRedis.setClassName(entity.getClassName());
		studentRedis.setName(entity.getName());
		studentRedis.setRoll(entity.getRoll());
		
		return studentRedis;
	}
	
	private List<Map.Entry<StudentKey, StudentEntity>> getAllStudentData(List<String>allHashKeys){
		
		Map<String,Map<String,String>> hmaps= getAllMapData(allHashKeys);
		
		List<Map.Entry<StudentKey, StudentEntity>>studentlist= new ArrayList();
		Map<StudentKey,StudentEntity> dataMap = new HashMap();
		for (Map.Entry<String, Map<String, String>> mp : hmaps.entrySet()) {
			String key = mp.getKey();
			Map<String,String>value= mp.getValue();
			if(value.containsKey("_class")) {
				value.remove("_class");
			}
			String ignoreKey = "";
			for(Map.Entry<String, String> studentMap: value.entrySet()) {
				if(studentMap.getValue().contains(":")) {
					ignoreKey=studentMap.getKey();
				}
				
			}
			StudentKey studentKey =  studentRedisKeyToStringConverter.convert(value.get(ignoreKey));
			if(!ignoreKey.isEmpty())
				value.remove(ignoreKey);
			
			StudentEntity studentEntity = convertMapToPojo(value);
			dataMap.put(studentKey, studentEntity);
		}
		for(Map.Entry<StudentKey, StudentEntity> mp: dataMap.entrySet()) {
			studentlist.add(mp);
		}
		
		return studentlist;
	}
			
	public List<String> getAllHashKeys(String strPattern,String strPatternIgnr){
		return studentDataRedisRepository.getAllHashKeys(strPattern,strPatternIgnr);
	}
	private Map<String,Map<String,String>> getAllMapData(List<String> allHashkeys){
		return studentDataRedisRepository.getAlldata(allHashkeys);
	}
	private StudentEntity convertMapToPojo(Map<String, String> value) {
		var type =   new TypeToken<HashMap>(){}.getType();
		StudentEntity studentRedis = new Gson().fromJson(new Gson().toJson(value,type), StudentEntity.class);
		return studentRedis;
	}
}
