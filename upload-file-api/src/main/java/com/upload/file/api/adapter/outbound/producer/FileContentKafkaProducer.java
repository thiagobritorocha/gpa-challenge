package com.upload.file.api.adapter.outbound.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FileContentKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public FileContentKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, String fileContent) {
        this.kafkaTemplate.send(topicName, fileContent);
    }
}
