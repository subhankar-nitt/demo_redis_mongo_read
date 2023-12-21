package com.example.mongo_redis_read.entity;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;



@Document
@Data
public class Student {

//	@BsonProperty("_id")
//    private Object _id;
    @BsonProperty("id")
    private String id;

    private String name;
    private String className;
    private String rollNo;

//    public Object get_id() {
//        return _id;
//    }
//
//    public void set_id(Object _id) {
//        this._id = _id;
//    }

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

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
	
}
