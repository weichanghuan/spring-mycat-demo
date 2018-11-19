package com.spring.demo.service;

import com.spring.demo.model.Person;

/**
 * Created by Administrator on 2018/8/1.
 */
public interface PersonService {
    int deleteByPrimaryKey(Long id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);

}
