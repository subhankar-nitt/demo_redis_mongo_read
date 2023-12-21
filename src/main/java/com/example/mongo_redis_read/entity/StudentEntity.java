package com.example.mongo_redis_read.entity;

import org.springframework.stereotype.Component;

@Component
public class StudentEntity {
	
	private String id;
	private String name;
	private String className;
	private String rollNo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRoll() {
		return rollNo;
	}
	public void setRoll(String roll) {
		this.rollNo = roll;
	}

}
