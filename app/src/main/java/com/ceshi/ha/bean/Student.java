package com.ceshi.ha.bean;

import java.util.List;

public class Student {

    public Student(List<Course> courses) {
        this.courses = courses;
    }

    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
