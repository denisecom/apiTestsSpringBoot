package api.v1;

import api.domain.Student;
import api.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    List<Student> getAllStudents(){
        return (List<Student>) studentRepository.findAll();
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody Student newStudent){
        return studentRepository.save(newStudent);
    }

    @GetMapping("/students/{id}")
    Student getOneStudent(@PathVariable Long id){
        return studentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Could not find student " + id));
    }

    @PutMapping("/students/{id}")
    Student updateStudent(@RequestBody Student newStudent, @PathVariable Long id){
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(newStudent.getName());
                    student.setRegisterNumber(newStudent.getRegisterNumber());
                    return studentRepository.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return studentRepository.save(newStudent);
                });
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id){
        studentRepository.deleteById(id);
    }
}
