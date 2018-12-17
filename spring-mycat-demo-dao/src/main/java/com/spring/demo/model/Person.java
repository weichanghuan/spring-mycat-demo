package com.spring.demo.model;

import java.io.Serializable;
import javax.xml.bind.annotation.*;

@XmlType(propOrder = {})
@XmlRootElement(name = "people")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class Person implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @XmlElement
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}