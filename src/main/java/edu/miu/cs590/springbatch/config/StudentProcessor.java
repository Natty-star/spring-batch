package edu.miu.cs590.springbatch.config;


import edu.miu.cs590.springbatch.model.Student;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.Month;


public class StudentProcessor implements ItemProcessor<Student,Student> {

    @Override
    public Student process(Student student) throws Exception {
        final String first = student.getFirst();
        final String last = student.getLast();
        final Double GPA = student.getGPA();
        final Integer AGE = student.getAGE();
        int year = LocalDate.now().getYear() - AGE;
        LocalDate date = LocalDate.of(year, Month.JANUARY,1);
        final Student students = new Student(first,last,GPA,date);
        return students;
    }
}
