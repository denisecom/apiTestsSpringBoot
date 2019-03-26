package api.controllers;

import api.domain.Course;
import api.services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/")
public class CoursesController {

    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping
    Course addCourse(@RequestBody Course newCourse){
        return courseService.addCourse(newCourse);
    }

    @GetMapping("{id}")
    Course getOneCourse(@PathVariable Long id){
        return courseService.getOneCourse(id);
    }

    @PutMapping("{id}")
    Course updateCourse(@RequestBody Course newCourse, @PathVariable Long id){
        return courseService.updateCourse(newCourse, id);
    }

    @DeleteMapping("{id}")
    void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}
