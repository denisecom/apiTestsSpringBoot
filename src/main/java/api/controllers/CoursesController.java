package api.controllers;

import api.domain.Course;
import api.services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursesController {

    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping("/courses")
    Course addCourse(@RequestBody Course newCourse){
        return courseService.addCourse(newCourse);
    }

    @GetMapping("/courses/{id}")
    Course getOneCourse(@PathVariable Long id){
        return courseService.getOneCourse(id);
    }

    @PutMapping("/courses/{id}")
    Course updateCourse(@RequestBody Course newCourse, @PathVariable Long id){
        return courseService.updateCourse(newCourse, id);
    }

    @DeleteMapping("/courses/{id}")
    void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}
