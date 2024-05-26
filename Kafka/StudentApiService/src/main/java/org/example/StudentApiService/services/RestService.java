package org.example.StudentApiService.services;

import org.example.StudentEntityService.dtos.*;

import java.util.List;

public interface RestService {
    List<StudentDTO> findAllStudents();

    List<StudentDTO> findAllExcellentStudents();

    List<AvgMarkDTO> topAvgMarkList();

    List<LessonAvgDTO> avgMarksByLesson();

    List<NumberOfMarksDTO> topNumberOfMarks();
}
