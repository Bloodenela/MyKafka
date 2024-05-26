package org.example.StudentApiService.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.StudentApiService.configs.KafkaProducerConfig;
import org.example.StudentApiService.services.KafkaMarkService;
import org.example.StudentEntityService.dtos.AddMarkDTO;
import org.example.StudentEntityService.dtos.MarkDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Setter
@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaMarkServiceImpl implements KafkaMarkService {
    private final KafkaProducerConfig kafkaConfig;

    @Override
    public void addMark(Long studentId, MarkDTO markDTO) {
        AddMarkDTO addMarkDTO = new AddMarkDTO();
        addMarkDTO.setStudentId(studentId);
        addMarkDTO.setMarkDTO(markDTO);

        Integer partition = markDTO.getMark() - 1;
        String key = String.valueOf(markDTO.getMark() - 1);

        KafkaTemplate<String, Object> kafkaTemplate = kafkaConfig.kafkaTemplate();
        String addMarkTopic = kafkaConfig.getAddMarkTopic();
        CompletableFuture<SendResult<String, Object>> completableFuture =

                kafkaTemplate.send(addMarkTopic, partition, key, addMarkDTO);

        completableFuture.whenComplete((stringObjectSendResult, throwable) -> {
            if (throwable == null) {
                System.out.println("Message sent");
            } else {
                throw new RuntimeException(throwable.getMessage());
            }
        });
    }
}
