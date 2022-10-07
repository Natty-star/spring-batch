package edu.miu.cs590.springbatch.dao;

import edu.miu.cs590.springbatch.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student,Long> {
}
