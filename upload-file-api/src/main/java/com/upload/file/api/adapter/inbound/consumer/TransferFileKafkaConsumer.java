package com.upload.file.api.adapter.inbound.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransferFileKafkaConsumer {

    @KafkaListener(topics = "transactionfile-saved", groupId = "transaction-group")
    public void consumeStringMessage(String message) {
        System.out.println("Received message: " + message);
        // Aqui você pode adicionar lógica para processar a mensagem recebida.
    }
}
