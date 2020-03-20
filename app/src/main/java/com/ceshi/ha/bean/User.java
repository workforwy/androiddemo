package com.ceshi.ha.bean;

/**
 * Created by rex on 2017/3/18 0018.
 */

public class User {
    private String age;
    private String name;
    private String gender;
    private String hobby;

    public User() {
    }

    public User(String age, String name, String gender, String hobby) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.hobby = hobby;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }


}
