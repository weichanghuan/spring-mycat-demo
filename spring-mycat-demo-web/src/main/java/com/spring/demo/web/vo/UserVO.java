package com.spring.demo.web.vo;

import io.swagger.annotations.ApiParam;

import java.io.Serializable;

public class UserVO implements Serializable {

    @ApiParam(value = "用户编号", required = false)
    private Long id;
    @ApiParam(value = "用户姓名", required = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
