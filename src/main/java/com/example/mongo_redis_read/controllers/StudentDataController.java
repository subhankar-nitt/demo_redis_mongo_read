package com.example.mongo_redis_read.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongo_redis_read.entity.Student;
import com.example.mongo_redis_read.entity.StudentRedis;
import com.example.mongo_redis_read.services.StudentDataRedisService;
import com.example.mongo_redis_read.services.StudentDataService;

@RestController
public class StudentDataController {
	
	@Autowired
	private StudentDataRedisService studentDataRedisService;
	
	@Autowired
	private StudentDataService studentDataService;
	
	@PostMapping("/rest/students")
	public List<StudentRedis> getAllStudent(){
		return studentDataRedisService.getAllStudentRedisData();
	}
	@PostMapping("/rest/studentsMongo")
	public List<Student> getAllStudentMongo(){
		return studentDataService.getAllStudentsData();
	}
}
