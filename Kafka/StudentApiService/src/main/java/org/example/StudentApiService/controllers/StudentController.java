package org.example.StudentApiService.controllers;

import lombok.RequiredArgsConstructor;
import org.example.StudentApiService.services.KafkaMarkService;
import org.example.StudentApiService.services.KafkaStudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.StudentApiService.services.RestService;
import org.example.StudentEntityService.dtos.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final RestService restService;
    private final KafkaMarkService kafkaMarkService;
    private final KafkaStudentService kafkaStudentService;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO student = kafkaStudentService.createStudent(studentDTO);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("addMark/{studentId}")
    public void addMark(@PathVariable Long studentId, @RequestBody MarkDTO markDTO) {
        kafkaMarkService.addMark(studentId, markDTO);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> findAllStudents() {
        List<StudentDTO> students = restService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/excellent")
    public ResponseEntity<List<StudentDTO>> findAllExcellentStudents() {
        List<StudentDTO> excellentStudents = restService.findAllExcellentStudents();
        return new ResponseEntity<>(excellentStudents, HttpStatus.OK);
    }

    @GetMapping("/topAvgMarks")
    public ResponseEntity<List<AvgMarkDTO>> topAvgMarkList() {
        List<AvgMarkDTO> topAvgMarkList = restService.topAvgMarkList();
        return new ResponseEntity<>(topAvgMarkList, HttpStatus.OK);
    }

    @GetMapping("/avgMarkByLesson")
    public ResponseEntity<List<LessonAvgDTO>> avgMarksByLesson() {
        List<LessonAvgDTO> lessonAvgDTOs = restService.avgMarksByLesson();
        return new ResponseEntity<>(lessonAvgDTOs, HttpStatus.OK);
    }

    @GetMapping("/topNumberOfMarks")
    public ResponseEntity<List<NumberOfMarksDTO>> topNumberOfMarks() {
        List<NumberOfMarksDTO> numberOfMarksDTOs = restService.topNumberOfMarks();
        return new ResponseEntity<>(numberOfMarksDTOs, HttpStatus.OK);
    }
}
