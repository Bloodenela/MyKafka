package org.example.StudentDataService.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.StudentEntityService.enums.Lesson;

@Entity
@Table(name = "marks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Student student;
    @Enumerated(EnumType.STRING)
    private Lesson lesson;
    private Integer mark;
}
