package api.v1;

import api.domain.Course;
import api.repositories.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CoursesController {

    private final CourseRepository courseRepository;

    public CoursesController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    List<Course> getAllCourses(){
        return (List<Course>) courseRepository.findAll();
    }

    @PostMapping("/courses")
    Course newCourse(@RequestBody Course newCourse){
        return courseRepository.save(newCourse);
    }

    @GetMapping("courses/{id}")
    Course getOneCourse(@PathVariable Long id){
        return courseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Could not find course " + id));
    }

    @PutMapping("/courses/{id}")
    Course updateCourse(@RequestBody Course newCourse, @PathVariable Long id){
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

    @DeleteMapping("/courses/{id}")
    void deleteCourse(@PathVariable Long id){
        courseRepository.deleteById(id);
    }
}
