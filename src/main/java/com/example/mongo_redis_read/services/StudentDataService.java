package com.example.mongo_redis_read.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mongo_redis_read.entity.Student;
import com.example.mongo_redis_read.repositories.StudentDataRepository;

@Service
public class StudentDataService {

	@Autowired
	private StudentDataRepository studentDataRepository;
	
	public List<Student> getAllStudentsData(){
		return studentDataRepository.getAllStudentData();
	}
}
