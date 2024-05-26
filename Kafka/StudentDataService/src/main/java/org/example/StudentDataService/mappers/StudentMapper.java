package org.example.StudentDataService.mappers;

import org.example.StudentDataService.entities.Mark;
import org.example.StudentDataService.entities.Student;
import org.example.StudentEntityService.dtos.MarkDTO;
import org.example.StudentEntityService.dtos.StudentDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.example.StudentDataService.entities.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public abstract class StudentMapper {
    public static StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    public abstract StudentDTO studentToStudentDTO(Student student);

    @AfterMapping
    protected void setEmptyList(@MappingTarget StudentDTO studentDTO) {
        if (studentDTO.getMarks() == null) {
            studentDTO.setMarks(new ArrayList<>());
        }
    }

    public abstract MarkDTO markToMarkDTO(Mark mark);

    public abstract List<MarkDTO> marksToMarkDTOs(List<Mark> marks);

    public abstract Student studentDTOToStudent(StudentDTO studentDTO);
}
