package api.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private String registerNumber;

    @ManyToOne
    @JoinColumn(name="courseId", nullable = false)
    private Course course;

}
