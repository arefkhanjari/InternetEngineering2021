package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@Lazy
public class TeacherService {

    private final DatabaseManager databaseManager;
    private final StateService stateService;

    @Autowired
    public TeacherService(DatabaseManager databaseManager, StateService stateService) {
        this.databaseManager = databaseManager;
        this.stateService = stateService;
    }

    public void register(String firstName, String lastName, String teacherId) {
        Teacher teacher = new Teacher(firstName, lastName, teacherId);
        boolean isSuccessful = databaseManager.registerTeacher(teacher);
        if (!isSuccessful) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot register teacher in database!");
        }
    }

    public ArrayList<Lesson> getTeacherLessons(String teacherId) {
        Integer currentState = stateService.getCurrentState();
        if (currentState != 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot view weekly classes in this state!");
        }

        return databaseManager.getTeacherLessons(teacherId);
    }
}
