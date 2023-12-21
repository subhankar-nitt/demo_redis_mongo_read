package com.example.mongo_redis_read.repositories.Impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.mongo_redis_read.entity.Student;
import com.example.mongo_redis_read.repositories.StudentDataRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class StudentDataRepositoryImpl implements StudentDataRepository {

	@Autowired
	private MongoCollection<Student> mongoCollection;
	@Override
	public List<Student> getAllStudentData() {
		List<Student> list = mongoCollection.find(new Document(),Student.class).into(new ArrayList<>());
		return list;
	}
	@Override
	public Student getStudentById(String fieldName,String fieldValue) {
		Bson filters = Filters.eq(fieldName,fieldValue);
		Student student=mongoCollection.find(filters).first();
		return student;
	}

}
