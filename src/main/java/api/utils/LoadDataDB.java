package api.utils;

import api.domain.Course;
import api.domain.Student;
import api.services.CourseService;
import api.services.StudentService;

public class LoadDataDB {

    CourseService courseService;
    StudentService studentService;

    public LoadDataDB(CourseService courseService, StudentService studentService){
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Course loadCoursesDB(String name){
        Course course = new Course();
        course.setName(name);
        courseService.addCourse(course);
        return course;
    }

    public Student loadStudentDB(Course course, String name, String registerNumber){
        Student student = new Student();
        student.setCourse(course);
        student.setName(name);
        student.setRegisterNumber(registerNumber);
        studentService.addStudent(course.getId(),student);
        return student;
    }
}
