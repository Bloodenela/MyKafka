package org.example.StudentEntityService.dtos;

import lombok.*;
import org.example.StudentEntityService.enums.Lesson;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LessonAvgDTO {
    private Lesson lesson;
    private Double avgMark;
}
