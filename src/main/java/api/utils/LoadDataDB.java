package api.utils;

import api.domain.Course;
import api.domain.Student;

public class LoadDataDB {

    public static Course loadCoursesDB(String name){
        Course course = new Course();
        course.setName(name);
        return course;
    }

    public static Student loadStudentDB(Course course, String name, String registerNumber){
        Student student = new Student();
        student.setCourse(course);
        student.setName(name);
        student.setRegisterNumber(registerNumber);
        return student;
    }
}
