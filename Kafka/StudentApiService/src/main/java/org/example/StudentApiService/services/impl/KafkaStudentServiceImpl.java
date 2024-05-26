package org.example.StudentApiService.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.StudentApiService.configs.KafkaProducerConfig;
import org.example.StudentApiService.services.KafkaStudentService;
import org.example.StudentEntityService.dtos.StudentDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Setter
@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaStudentServiceImpl implements KafkaStudentService {
    private final KafkaProducerConfig kafkaConfig;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        String key = UUID.randomUUID().toString();

        KafkaTemplate<String, Object> kafkaTemplate = kafkaConfig.kafkaTemplate();
        String studentsTopic = kafkaConfig.getStudentsTopic();
        CompletableFuture<SendResult<String, Object>> completableFuture =

                kafkaTemplate.send(studentsTopic, key, studentDTO);

        completableFuture.whenComplete((stringObjectSendResult, throwable) -> {
            if (throwable == null) {
                System.out.println("Message sent");
            }
        });
        return studentDTO;
    }
}
