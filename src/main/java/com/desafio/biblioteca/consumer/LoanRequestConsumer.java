package com.desafio.biblioteca.consumer;

import com.desafio.biblioteca.dto.LoanBatchRequestDto;
import com.desafio.biblioteca.service.LoanService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LoanRequestConsumer {

    private final LoanService loanService;

    public LoanRequestConsumer(LoanService loanService) {
        this.loanService = loanService;
    }

    @KafkaListener(topics = "loan-requests", groupId = "loan-processor")
    public void consume(LoanBatchRequestDto batch) {
        loanService.processBatch(batch);
    }
}
