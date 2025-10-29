package com.desafio.biblioteca.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic loanRequestsTopic() {
        return new NewTopic("loan-requests", 1, (short) 1);
    }

    @Bean
    NewTopic loanResultsTopic() {
        return new NewTopic("loan-results", 1, (short) 1);
    }

    @Bean
    NewTopic loanDlqTopic() {
        return new NewTopic("loan-dlq", 1, (short) 1);
    }
}
