package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@Lazy
public class StudentService {

    private final DatabaseManager databaseManager;
    private final StateService stateService;

    @Autowired
    public StudentService(DatabaseManager databaseManager, StateService stateService) {
        this.databaseManager = databaseManager;
        this.stateService = stateService;
    }

    public void register(String firstName, String lastName, String nationalCode, String studentId) {
        Student student = new Student(firstName, lastName, nationalCode, studentId);
        boolean isSuccessful = databaseManager.registerStudent(student);
        if (!isSuccessful) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot register student in database!");
        }
    }

    public void selectLesson(String studentId, String lessonId) {
        Integer currentState = stateService.getCurrentState();
        if (currentState != 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot select lesson in this state!");
        }

        // Conflict checking start
        Lesson lessonToSelect = databaseManager.getLesson(lessonId);
        if (lessonToSelect == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot find lesson in database!");
        }

        ArrayList<Lesson> studentLessons = getStudentLessons(studentId, false);
        for (Lesson lesson: studentLessons) {
            if (hasConflict(lesson, lessonToSelect)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot select lesson because of conflict!");
            }
        }
        // Conflict checking end

        boolean isSuccessful = databaseManager.selectLesson(studentId, lessonId);
        if (!isSuccessful) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot select the lesson for student in database!");
        }
    }

    private boolean hasConflict(Lesson lesson, Lesson lessonToSelect) {
        if (lesson.getClassDay() == lessonToSelect.getClassDay() && lesson.getClassTime() == lessonToSelect.getClassTime()) {
            return true;
        }
        if (lesson.getExamDate() == lessonToSelect.getExamDate() && lesson.getExamTime() == lessonToSelect.getExamTime()) {
            return true;
        }
        return false;
    }

    public ArrayList<Lesson> getStudentLessons(String studentId, boolean checkState) {
        Integer currentState = stateService.getCurrentState();
        if (currentState != 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot view weekly classes in this state!");
        }

        return databaseManager.getStudentLessons(studentId);
    }
}
