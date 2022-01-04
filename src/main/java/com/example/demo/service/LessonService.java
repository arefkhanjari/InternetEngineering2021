package com.example.demo.service;

import com.example.demo.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Lazy
public class LessonService {

    private final DatabaseManager databaseManager;

    @Autowired
    public LessonService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void add(String title, String teacherId, Integer classDay, Integer classTime, String examDate, Integer examTime, String lessonId) {
        Lesson lesson = new Lesson(title, teacherId, classDay, classTime, examDate, examTime, lessonId);
        boolean isSuccessful = databaseManager.addLesson(lesson);
        if (!isSuccessful) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot add lesson in database!");
        }
    }

    public void setMark(String lessonId, String studentId, Double mark) {
        boolean isSuccessful = databaseManager.setMark(lessonId, studentId, mark);
        if (!isSuccessful) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot set mark in database!");
        }
    }

    public String getMark(String lessonId, String studentId) {
        String mark = databaseManager.getMark(lessonId, studentId);
        if (mark == null) {
            mark = "No mark set yet!";
        }
        return mark;
    }
}
