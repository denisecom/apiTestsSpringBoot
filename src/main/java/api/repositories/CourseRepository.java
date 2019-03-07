package api.repositories;

import api.domain.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long>{

    Course findByName(String name);
}
