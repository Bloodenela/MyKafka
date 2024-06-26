package org.example.StudentDataService.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.StudentDataService.entities.Mark;
import org.example.StudentDataService.mappers.StudentMapper;
import org.example.StudentDataService.repositories.StudentRepository;
import org.example.StudentEntityService.dtos.*;
import org.springframework.stereotype.Service;
import org.example.StudentDataService.entities.Student;
import org.example.StudentDataService.services.StudentService;
import org.example.StudentEntityService.enums.Lesson;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        List<Mark> marks = student.getMarks().stream()
                .peek(mark -> mark.setStudent(student))
                .toList();
        student.setMarks(marks);
        Student save = studentRepository.save(student);
        return studentMapper.studentToStudentDTO(save);
    }

    @Override
    public void addMark(Long studentId, MarkDTO markDTO) {
        Student student = studentRepository.findById(studentId).orElseThrow();

        Mark newMark = new Mark();
        newMark.setMark(markDTO.getMark());
        newMark.setLesson(markDTO.getLesson());
        newMark.setStudent(student);

        student.getMarks().add(newMark);

        Student save = studentRepository.save(student);
    }

    @Override
    public List<StudentDTO> findAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::studentToStudentDTO)
                .toList();
    }

    @Override
    public StudentDTO findById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        return studentMapper.studentToStudentDTO(student);
    }

    @Override
    public List<StudentDTO> findAllExcellentStudents() {
        List<StudentDTO> excellentStudents = new ArrayList<>();
        List<StudentDTO> students = this.findAllStudents();
        for (StudentDTO student : students) {
            List<MarkDTO> marks = student.getMarks();
            OptionalDouble average = marks.stream()
                    .map(MarkDTO::getMark)
                    .mapToDouble(Integer::doubleValue)
                    .average();
            if (average.isEmpty()) {
                continue;
            }
            double epsilon = 0.000001d;
            if (Math.abs(average.getAsDouble() - 5.0) < epsilon) {
                excellentStudents.add(student);
            }
        }
        return excellentStudents;
    }

    @Override
    public List<AvgMarkDTO> topAvgMarkList() {
        List<AvgMarkDTO> sorted = this.findAllStudents().stream()
                .map(this::getAvgMarkDTO)
                .sorted(Comparator.comparingDouble(AvgMarkDTO::getAvgMark).reversed())
                .toList();
        return sorted.size() >= 3 ? sorted.subList(0, 3) : sorted;
    }

    private AvgMarkDTO getAvgMarkDTO(StudentDTO s) {
        AvgMarkDTO avgMarkDTO = new AvgMarkDTO();
        avgMarkDTO.setId(s.getId());
        avgMarkDTO.setName(s.getName());

        OptionalDouble average = s.getMarks().stream()
                .map(MarkDTO::getMark)
                .mapToDouble(Integer::doubleValue)
                .average();

        avgMarkDTO.setAvgMark(average.orElse(0.0));

        return avgMarkDTO;
    }

    @Override
    public List<LessonAvgDTO> avgMarksByLesson() {
        Map<Lesson, Double> counts = new HashMap<>();
        Map<Lesson, Double> sums = new HashMap<>();
        for (Lesson lesson : Lesson.values()) {
            counts.put(lesson, 0D);
            sums.put(lesson, 0D);
        }
        for (StudentDTO studentDTO : this.findAllStudents()) {
            studentDTO.getMarks().forEach(markDTO -> {
                        for (Lesson lesson : Lesson.values()) {
                            if (lesson.equals(markDTO.getLesson())) {
                                Double sum = sums.get(lesson);
                                Double count = counts.get(lesson);
                                int mark = markDTO.getMark();
                                sums.put(lesson, sum + mark);
                                counts.put(lesson, count + 1);
                                break;
                            }
                        }
                    }
            );
        }
        return Stream.of(Lesson.values())
                .map(lesson -> {
                    double avgMark = counts.get(lesson) == 0D ? 0 : sums.get(lesson) / counts.get(lesson);
                    return LessonAvgDTO.builder()
                            .lesson(lesson)
                            .avgMark(avgMark)
                            .build();
                }).toList();
    }

    @Override
    public List<NumberOfMarksDTO> topNumberOfMarks() {
        List<NumberOfMarksDTO> numberOfMarksDTOS = new ArrayList<>(
                this.findAllStudents().stream()
                        .map(studentDTO -> NumberOfMarksDTO.builder()
                                .id(studentDTO.getId())
                                .name(studentDTO.getName())
                                .numberOfMarks(studentDTO.getMarks().size())
                                .build())
                        .sorted(Comparator.comparingInt(NumberOfMarksDTO::getNumberOfMarks).reversed())
                        .toList()
        );
        return numberOfMarksDTOS.size() >= 3 ? numberOfMarksDTOS.subList(0, 3) : numberOfMarksDTOS;

    }
}
