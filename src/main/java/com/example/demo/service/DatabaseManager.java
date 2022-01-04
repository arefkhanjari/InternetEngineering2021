package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service
@Lazy
public class DatabaseManager {

    private final String url = "jdbc:mysql://localhost:3306/University";
    private final String user;
    private final String pass;

    @Autowired
    public DatabaseManager(
            @Value("${database-username:root}") String user,
            @Value("${database-password:123}") String pass
    ) {
        this.user = user;
        this.pass = pass;
    }

    public boolean registerStudent(Student student) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "INSERT INTO students VALUES ('"
                    + student.getId() + "', '"
                    + student.getFirstName() + "', '"
                    + student.getLastName() + "', '"
                    + student.getNationalCode() + "');";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean registerTeacher(Teacher teacher) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "INSERT INTO teachers VALUES ('"
                    + teacher.getId() + "', '"
                    + teacher.getFirstName() + "', '"
                    + teacher.getLastName() + "');";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addLesson(Lesson lesson) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "INSERT INTO lessons VALUES ('"
                    + lesson.getId() + "', '"
                    + lesson.getTitle() + "', '"
                    + lesson.getTeacherId() + "', "
                    + lesson.getClassDay() + ", "
                    + lesson.getClassTime() + ", '"
                    + lesson.getExamDate() + "', "
                    + lesson.getExamTime() + ");";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean selectLesson(String studentId, String lessonId) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "INSERT INTO studentslessons VALUES (" + studentId + ", " + lessonId + ", null);";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Integer getState() {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM state limit 1;";

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt("current");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean setState(Integer state) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "UPDATE state set current = " + state + ";";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Lesson> getStudentLessons(String studentId) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM lessons WHERE id in (SELECT lessonId FROM StudentsLessons WHERE studentId = " + studentId + ");";

            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Lesson> lessonList = new ArrayList<>();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String teacherId = resultSet.getString("teacherId");
                Integer classDay = resultSet.getInt("classDay");
                Integer classTime = resultSet.getInt("classTime");
                String examDate = resultSet.getString("examDate");
                Integer examTime = resultSet.getInt("examTime");
                String lessonId = resultSet.getString("id");

                Lesson lesson = new Lesson(title, teacherId, classDay, classTime, examDate, examTime, lessonId);
                lessonList.add(lesson);
            }
            return lessonList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Lesson> getTeacherLessons(String teacherId) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM lessons WHERE teacherId = " + teacherId + ";";

            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Lesson> lessonList = new ArrayList<>();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                Integer classDay = resultSet.getInt("classDay");
                Integer classTime = resultSet.getInt("classTime");
                String examDate = resultSet.getString("examDate");
                Integer examTime = resultSet.getInt("examTime");
                String lessonId = resultSet.getString("id");

                Lesson lesson = new Lesson(title, teacherId, classDay, classTime, examDate, examTime, lessonId);
                lessonList.add(lesson);
            }
            return lessonList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setMark(String lessonId, String studentId, Double mark) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "UPDATE studentslessons set mark = " + mark + " WHERE lessonId = " + lessonId + " AND studentId = " + studentId + ";";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getMark(String lessonId, String studentId) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "SELECT mark FROM studentslessons WHERE lessonId = " + lessonId + " AND studentId = " + studentId + ";";

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getDouble("mark") + "";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Lesson getLesson(String lessonId) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM lessons WHERE id = " + lessonId + ";";

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            String title = resultSet.getString("title");
            String teacherId = resultSet.getString("teacherId");
            Integer classDay = resultSet.getInt("classDay");
            Integer classTime = resultSet.getInt("classTime");
            String examDate = resultSet.getString("examDate");
            Integer examTime = resultSet.getInt("examTime");

            return new Lesson(title, teacherId, classDay, classTime, examDate, examTime, lessonId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}