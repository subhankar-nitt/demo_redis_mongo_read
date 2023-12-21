package com.example.mongo_redis_read.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.mongo_redis_read.entity.Student;

@Repository
public interface StudentDataRepository {

	public List<Student>getAllStudentData();
	public Student getStudentById(String fieldName,String fieldValue) ;
}
