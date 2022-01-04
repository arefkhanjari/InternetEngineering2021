package com.example.demo.controller;

import com.example.demo.Validation;
import com.example.demo.model.Lesson;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("teacher/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String teacherId
    ) {
        Validation.validateName(firstName);
        Validation.validateName(lastName);
        Validation.validateId(teacherId);

        teacherService.register(firstName, lastName, teacherId);

        return "OK";
    }

    @GetMapping("teacher/classes")
    public String teacherClasses(@RequestParam String teacherId) {
        Validation.validateId(teacherId);

        ArrayList<Lesson> teacherLessons = teacherService.getTeacherLessons(teacherId);

        String page = "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Teacher Weekly Classes</title>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    </head>\n" +
                "    <body>\n";

        for (Lesson lesson : teacherLessons) {
            String text = "Title: " + lesson.getTitle()
                    + " -------------- Class day: " + lesson.getClassDayText()
                    + " -------------- Class time: " + lesson.getClassTime();

            page += "<p>\n" + text + "</p>\n";
        }

        page += "</body>\n" + "</html>";
        return page;
    }

    @GetMapping("teacher/exams")
    public String teacherExams(@RequestParam String teacherId) {
        Validation.validateId(teacherId);

        ArrayList<Lesson> teacherLessons = teacherService.getTeacherLessons(teacherId);

        String page = "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Teacher Exams</title>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    </head>\n" +
                "    <body>\n";

        for (Lesson lesson : teacherLessons) {
            String text = "Title: " + lesson.getTitle()
                    + " -------------- Exam date: " + lesson.getExamDate()
                    + " -------------- Exam time: " + lesson.getExamTime();

            page += "<p>\n" + text + "</p>\n";
        }

        page += "</body>\n" + "</html>";
        return page;
    }
}
