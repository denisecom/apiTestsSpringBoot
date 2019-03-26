package api.controllers;

import api.domain.Student;
import api.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/students/")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    List<Student> getAllStudents(@PathVariable Long courseId){
        return studentService.getAllStudents(courseId);
    }

    @PostMapping
    Student addStudent(@PathVariable Long courseId, @RequestBody Student newStudent){
        return studentService.addStudent(courseId, newStudent);
    }

    @GetMapping("{studentId}")
    Student getOneStudent(@PathVariable Long courseId, @PathVariable Long studentId){
        return studentService.getOneStudent(courseId, studentId);
    }

    @PutMapping("{studentId}")
    Student updateStudent(@RequestBody Student newStudent, @PathVariable Long courseId, @PathVariable Long studentId){
        return studentService.updateStudent(newStudent, courseId, studentId);
    }

    @DeleteMapping("{studentId}")
    void deleteStudent(@PathVariable Long courseId, @PathVariable Long studentId){
        studentService.deleteStudent(courseId, studentId);
    }
}
