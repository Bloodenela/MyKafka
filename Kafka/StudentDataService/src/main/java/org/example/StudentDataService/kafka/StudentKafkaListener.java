package org.example.StudentDataService.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.example.StudentDataService.services.StudentService;
import org.example.StudentEntityService.dtos.StudentDTO;
import org.example.StudentEntityService.dtos.AddMarkDTO;

@Component
@RequiredArgsConstructor
public class StudentKafkaListener {
    private final StudentService studentService;

    @KafkaListener(topics = {"students"}, groupId = "1")
    public void studentsListener(StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
    }

    @KafkaListener(topics = {"add_mark"}, groupId = "1")
    public void marksListener(AddMarkDTO addMarkDTO) {
        studentService.addMark(addMarkDTO.getStudentId(), addMarkDTO.getMarkDTO());
    }
}
