package api.services;

import api.domain.Course;
import api.domain.Student;
import api.repositories.StudentRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService{

    private StudentRepository studentRepository;
    private CourseService courseService;

    public StudentService(StudentRepository studentRepository, @Lazy CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public List<Student> getAllStudents(Long courseId){
        courseService.validateIfCourseExists(courseId);
        return getByCourseId(courseId);
    }

    public Student addStudent(Long courseId, Student newStudent){
        Course course = courseService.getOneCourse(courseId);
        newStudent.setCourse(course);
        return studentRepository.save(newStudent);
    }

    public Student getOneStudent(Long courseId, Long studentId){
        courseService.validateIfCourseExists(courseId);
        return studentRepository.findById(studentId)
                .orElseThrow(()-> new RuntimeException("Could not find student " + studentId));
    }

    public List<Student> getByCourseId(Long courseId){
        return studentRepository.findByCourseId(courseId);
    }

    public Student getByRegisterNumber(String registerNumber){
        return studentRepository.findByRegisterNumber(registerNumber);
    }

    public Student getByName(String name){
        return studentRepository.findByName(name);
    }

    public Student updateStudent(Student newStudent, Long courseId, Long studentId){
        courseService.validateIfCourseExists(courseId);
        Course course = courseService.getOneCourse(courseId);
        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setName(newStudent.getName());
                    student.setRegisterNumber(newStudent.getRegisterNumber());
                    student.setCourse(course);
                    return studentRepository.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(studentId);
                    return studentRepository.save(newStudent);
                });
    }

    public void deleteStudent(Long courseId, Long studentId){
        courseService.validateIfCourseExists(courseId);
        studentRepository.deleteById(studentId);
    }

    public void deleteAllStudents(){
        studentRepository.deleteAll();
    }

    public void deleteAllByCourseId(Long courseId){
        List<Student> students = studentRepository.findByCourseId(courseId);
        studentRepository.deleteAll(students);
    }
}
