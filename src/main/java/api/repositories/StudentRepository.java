package api.repositories;

import api.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long>{

    Student findByName(String name);
    Student findByRegisterNumber(String registerNumber);
    List<Student> findByCourseId(Long id);
}
