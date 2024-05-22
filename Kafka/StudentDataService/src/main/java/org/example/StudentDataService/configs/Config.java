package org.example.StudentDataService.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.example.StudentEntityService.dtos.MarkDTO;
import org.example.StudentEntityService.dtos.StudentDTO;
import org.example.StudentEntityService.enums.Lesson;

import java.util.List;

@Configuration
public class Config {
    @Bean
    public List<Lesson> lesson() {
        return List.of(Lesson.values());
    }

    @Bean
    public StudentDTO studentDTO() {
        return new StudentDTO();
    }

    @Bean
    public MarkDTO markDTO() {
        return new MarkDTO();
    }
}
