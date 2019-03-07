package api.repositories;

import api.domain.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long>{

    Student findByName(String name);
    Student findByRegisterNumber(String registerNumber);
}
