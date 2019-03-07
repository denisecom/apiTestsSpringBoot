package api.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String registerNumber;

    protected Student(){
    }

    public Student(String name, String registerNumber){
        this.name = name;
        this.registerNumber = registerNumber;
    }

}
