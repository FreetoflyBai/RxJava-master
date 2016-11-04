package com.android.rxjava;

/**
 * Description:
 * Author     : kevin.bai
 * Time       : 2016/11/4 18:05
 * QQ         : 904869397@qq.com
 */

public class Student {
    private String name;
    private int age;
    private Course[] course;

    public Student(String name, int age ,Course[] course) {
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Course[] getCourse() {
        return course;
    }

    public void setCourse(Course[] course) {
        this.course = course;
    }

    public static class Course{
        private String courseName;

        public Course(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }


}
