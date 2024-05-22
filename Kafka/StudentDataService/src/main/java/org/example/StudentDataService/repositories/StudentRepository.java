package org.example.StudentDataService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.StudentDataService.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
