package com.spring.demo.service.impl;

import com.spring.demo.dao.LogMapper;
import com.spring.demo.model.Log;
import com.spring.demo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogMapper logMapper;

    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    public int insert(Log record) {
        return logMapper.insert(record);
    }

    public int insertSelective(Log record) {
        return logMapper.insertSelective(record);
    }

    public Log selectByPrimaryKey(Long id) {
        return logMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Log record) {
        return 0;
    }

    public int updateByPrimaryKey(Log record) {
        return 0;
    }
}
