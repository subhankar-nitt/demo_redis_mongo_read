package com.example.mongo_redis_read.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RedisHash("STUDENT_TABLE_REDIS_HASH")
@Data
public class StudentRedis {

	@Id
	private StudentKey studentKey;
	private String name;
	private String id;
	private String rollNo;
	private String className;

	public StudentRedis(){};
	public StudentRedis(StudentKey studentKey,String name,String id,String rollNo,String className){
		this.studentKey=studentKey;
		this.id=id;
		this.name=name;
		this.rollNo=rollNo;
		this.className=className;
	}
	public StudentKey getStudentKey() {
		return studentKey;
	}
	public void setStudentKey(StudentKey studentKey) {
		this.studentKey = studentKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoll() {
		return rollNo;
	}
	public void setRoll(String roll) {
		this.rollNo = roll;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
