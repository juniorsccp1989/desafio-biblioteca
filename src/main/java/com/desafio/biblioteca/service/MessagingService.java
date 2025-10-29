package com.desafio.biblioteca.service;

import com.desafio.biblioteca.dto.LoanBatchRequestDto;
import com.desafio.biblioteca.dto.LoanResultDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MessagingService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLoanRequest(LoanBatchRequestDto dto) {
        kafkaTemplate.send("loan-requests", dto.getRequestId(), dto);
    }

    public void sendLoanResult(LoanResultDto result) {
        kafkaTemplate.send("loan-results", result.getBookId().toString(), result);
    }

    public void sendToDlq(String requestId, String reason) {
        kafkaTemplate.send("loan-dlq", requestId, reason);
    }
}
