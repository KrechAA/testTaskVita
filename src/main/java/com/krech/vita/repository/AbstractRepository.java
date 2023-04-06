package com.krech.vita.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractRepository {


    @Autowired
    private SessionFactory sessionFactory;


    protected AbstractRepository() {
    }

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }





}
