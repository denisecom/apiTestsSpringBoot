package api.services;

import api.domain.Course;
import api.repositories.CourseRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService{

    private CourseRepository courseRepository;
    private StudentService studentService;

    public CourseService(CourseRepository courseRepository, @Lazy StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public List<Course> getAllCourses(){
        return (List<Course>) courseRepository.findAll();
    }

    public Course addCourse(Course newCourse){
        return courseRepository.save(newCourse);
    }

    public Course getOneCourse(Long id){
        return courseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Could not find course " + id));
    }

    public Course getByName(String courseName){
        return courseRepository.findByName(courseName);
    }

    public Course updateCourse(Course newCourse, Long id){
        return courseRepository.findById(id)
                .map(course -> {
                    course.setName(newCourse.getName());
                    return courseRepository.save(course);
                })
                .orElseGet(() -> {
                    newCourse.setId(id);
                    return courseRepository.save(newCourse);
                });
    }

    public void deleteCourse(Long id){
        studentService.deleteAllByCourseId(id);
        courseRepository.deleteById(id);
    }

    public void deleteAllCourses(){
        studentService.deleteAllStudents();
        courseRepository.deleteAll();
    }

    public void validateIfCourseExists(Long id){
        if(!courseRepository.existsById(id)){
           throw new RuntimeException("Could not find course " + id);
        }
    }
}
