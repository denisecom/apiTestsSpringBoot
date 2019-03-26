package api.controllers;

import api.domain.Student;
import api.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/courses/{courseId}/students")
    List<Student> getAllStudents(@PathVariable Long courseId){
        return studentService.getAllStudents(courseId);
    }

    @PostMapping("/courses/{courseId}/students")
    Student addStudent(@PathVariable Long courseId, @RequestBody Student newStudent){
        return studentService.addStudent(courseId, newStudent);
    }

    @GetMapping("/courses/{courseId}/students/{studentId}")
    Student getOneStudent(@PathVariable Long courseId, @PathVariable Long studentId){
        return studentService.getOneStudent(courseId, studentId);
    }

    @PutMapping("/courses/{courseId}/students/{studentId}")
    Student updateStudent(@RequestBody Student newStudent, @PathVariable Long courseId, @PathVariable Long studentId){
        return studentService.updateStudent(newStudent, courseId, studentId);
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    void deleteStudent(@PathVariable Long courseId, @PathVariable Long studentId){
        studentService.deleteStudent(courseId, studentId);
    }
}
