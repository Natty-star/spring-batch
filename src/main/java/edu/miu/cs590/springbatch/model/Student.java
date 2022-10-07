package edu.miu.cs590.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String first;
    private String last;
    private Double GPA;
    private LocalDate DOB;
    @Transient
    private Integer AGE;

    public Student(String first, String last, Double GPA, LocalDate DOB) {
        this.first = first;
        this.last = last;
        this.GPA = GPA;
        this.DOB = DOB;
    }
}
