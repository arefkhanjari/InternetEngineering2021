package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {

    public static void validateNationalCode(String nationalCode) {
        if (nationalCode.length() != 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NationalCode is invalid!");
        }
    }

    public static void validateName(String name) {
        if (name.isEmpty() || name.length() > 30) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is invalid!");
        }
    }

    public static void validateId(String id) {
        if (id.length() != 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is invalid!");
        }
    }

    public static void validateWeekDay(Integer day) {
        if (day < 0 || day > 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "WeekDay is invalid!");
        }
    }

    public static void validateDayTime(Integer time) {
        if (time < 8 || time > 18) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DayTime is invalid!");
        }
    }

    public static void validateDate(String date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is invalid!");
        }
    }

    public static void validateMark(Double mark) {
        if (mark < 0 || mark > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mark is invalid!");
        }
    }
}
