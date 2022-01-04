package com.example.demo.controller;

import com.example.demo.Validation;
import com.example.demo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("lesson/add")
    public String add(
            @RequestParam String title,
            @RequestParam String teacherId,
            @RequestParam Integer classDay,
            @RequestParam Integer classTime,
            @RequestParam String examDate,
            @RequestParam Integer examTime,
            @RequestParam String lessonId
    ) {
        Validation.validateName(title);
        Validation.validateId(teacherId);
        Validation.validateWeekDay(classDay);
        Validation.validateDayTime(classTime);
        Validation.validateDate(examDate);
        Validation.validateDayTime(examTime);
        Validation.validateId(lessonId);

        lessonService.add(title, teacherId, classDay, classTime, examDate, examTime, lessonId);

        return "OK";
    }

    @GetMapping("lesson/setMark")
    public String setMark(
            @RequestParam String lessonId,
            @RequestParam String studentId,
            @RequestParam Double mark
    ) {
        Validation.validateId(lessonId);
        Validation.validateId(studentId);
        Validation.validateMark(mark);

        lessonService.setMark(lessonId, studentId, mark);

        return "OK";
    }

    @GetMapping("lesson/getMark")
    public String getMark(
            @RequestParam String lessonId,
            @RequestParam String studentId
    ) {
        Validation.validateId(lessonId);
        Validation.validateId(studentId);

        return lessonService.getMark(lessonId, studentId);
    }
}
