package org.example.StudentApiService.services;

import org.example.StudentEntityService.dtos.MarkDTO;

public interface KafkaMarkService {
    void addMark(Long studentId, MarkDTO markDTO);
}
