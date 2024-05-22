package org.example.StudentEntityService.dtos;


import lombok.*;
import org.example.StudentEntityService.enums.Lesson;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MarkDTO {
    private Lesson lesson;
    private Integer mark;
}
