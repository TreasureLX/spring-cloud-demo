package com.lx.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/14.
 */
public class User implements Serializable {

    private String userName;
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
