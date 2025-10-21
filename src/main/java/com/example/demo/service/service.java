package com.example.demo.service;

import org.hibernate.cfg.Environment;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class service implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
