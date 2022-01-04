package com.example.demo.model;

public class Teacher {
    private String firstName;
    private String lastName;
    private String id;

    public Teacher(String firstName, String lastName, String teacherId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
