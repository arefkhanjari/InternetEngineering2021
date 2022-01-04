package com.example.demo.controller;

import com.example.demo.Validation;
import com.example.demo.model.Lesson;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("student/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String nationalCode,
            @RequestParam String studentId
    ) {
        Validation.validateName(firstName);
        Validation.validateName(lastName);
        Validation.validateNationalCode(nationalCode);
        Validation.validateId(studentId);

        studentService.register(firstName, lastName, nationalCode, studentId);

        return "OK";
    }

    @GetMapping("student/selectLesson")
    public String selectLesson(
            @RequestParam String studentId,
            @RequestParam String lessonId
    ) {
        Validation.validateId(studentId);
        Validation.validateId(lessonId);

        studentService.selectLesson(studentId, lessonId);

        return "OK";
    }

    @GetMapping("student/classes")
    public String studentClasses(@RequestParam String studentId) {
        Validation.validateId(studentId);

        ArrayList<Lesson> studentLessons = studentService.getStudentLessons(studentId, true);

        String page = "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Student Weekly Classes</title>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    </head>\n" +
                "    <body>\n";

        for (Lesson lesson : studentLessons) {
            String text = "Title: " + lesson.getTitle()
                    + " -------------- Class day: " + lesson.getClassDayText()
                    + " -------------- Class time: " + lesson.getClassTime();

            page += "<p>\n" + text + "</p>\n";
        }

        page += "</body>\n" + "</html>";
        return page;
    }

    @GetMapping("student/exams")
    public String studentExams(@RequestParam String studentId) {
        Validation.validateId(studentId);

        ArrayList<Lesson> studentLessons = studentService.getStudentLessons(studentId, true);

        String page = "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Student Exams</title>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    </head>\n" +
                "    <body>\n";

        for (Lesson lesson : studentLessons) {
            String text = "Title: " + lesson.getTitle()
                    + " -------------- Exam date: " + lesson.getExamDate()
                    + " -------------- Exam time: " + lesson.getExamTime();

            page += "<p>\n" + text + "</p>\n";
        }

        page += "</body>\n" + "</html>";
        return page;
    }
}
