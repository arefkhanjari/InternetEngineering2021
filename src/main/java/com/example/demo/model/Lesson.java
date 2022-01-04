package com.example.demo.model;

public class Lesson {
    private String title;
    private String teacherId;
    private Integer classDay;
    private Integer classTime;
    private String examDate;
    private Integer examTime;
    private String id;

    public Lesson(String title, String teacherId, Integer classDay, Integer classTime, String examDate, Integer examTime, String lessonId) {
        this.title = title;
        this.teacherId = teacherId;
        this.classDay = classDay;
        this.classTime = classTime;
        this.examDate = examDate;
        this.examTime = examTime;
        this.id = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getClassDay() {
        return classDay;
    }

    public void setClassDay(Integer classDay) {
        this.classDay = classDay;
    }

    public Integer getClassTime() {
        return classTime;
    }

    public void setClassTime(Integer classTime) {
        this.classTime = classTime;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    public String getClassDayText() {
        switch (classDay) {
            case 0:
                return "Saturday";
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
        }
        return "Unknown: " + classDay;
    }
}
