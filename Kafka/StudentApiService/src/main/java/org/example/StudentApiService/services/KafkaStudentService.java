package org.example.StudentApiService.services;

import org.example.StudentEntityService.dtos.StudentDTO;

public interface KafkaStudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
}
