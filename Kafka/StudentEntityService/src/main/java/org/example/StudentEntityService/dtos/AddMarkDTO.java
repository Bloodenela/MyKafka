package org.example.StudentEntityService.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddMarkDTO {
    private Long studentId;
    private MarkDTO markDTO;
}
