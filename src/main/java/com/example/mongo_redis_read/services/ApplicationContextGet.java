package com.example.mongo_redis_read.services;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextGet implements ApplicationContextAware{

	private static ApplicationContext appContext;

	@Override
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)
			throws BeansException {
		appContext=applicationContext;
		
	}
	public static ApplicationContext getApplicationContext() {
		return appContext;
	}
	
}
