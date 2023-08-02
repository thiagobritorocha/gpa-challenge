package com.upload.file.api.domain.usecase;

import com.upload.file.api.adapter.outbound.producer.FileContentKafkaProducer;
import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.exception.Error;
import com.upload.file.api.domain.ports.inbound.UploadTransactionFileUseCasePort;
import com.upload.file.api.domain.ports.outbound.CreateTransactionFileAdapterPort;
import com.upload.file.api.domain.ports.outbound.FindTransactionFileAdapterPort;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadTransactionFileUseCase implements UploadTransactionFileUseCasePort {

    private final FindTransactionFileAdapterPort findTransactionFileAdapterPort;
    private final CreateTransactionFileAdapterPort createTransactionFileAdapterPort;

    private final FileContentKafkaProducer fileContentKafkaProducer;

    private final String topicName;

    public UploadTransactionFileUseCase(
            FindTransactionFileAdapterPort findTransactionFileAdapterPort,
            CreateTransactionFileAdapterPort createTransactionFileAdapterPort,
            FileContentKafkaProducer fileContentKafkaProducer,
            @Value("${kafka.topic.transactionfile-saved}") String topicName) {
        this.findTransactionFileAdapterPort = findTransactionFileAdapterPort;
        this.createTransactionFileAdapterPort = createTransactionFileAdapterPort;
        this.fileContentKafkaProducer = fileContentKafkaProducer;
        this.topicName = topicName;
    }

    @Override
    @Transactional
    public TransactionFile execute(TransactionFile transactionFile) {
        TransactionFile transactionFileResult =
                findTransactionFileAdapterPort.execute(transactionFile.getFileName());
        if (transactionFileResult != null) {
            throw Error.DATA_INTEGRITY_VIOLATION.toBusinessException();
        }

        TransactionFile transactionFileSaved =
                createTransactionFileAdapterPort.execute(transactionFile);

        fileContentKafkaProducer.sendMessage(
                topicName, readFileContent(transactionFile.getFileData()));

        return transactionFileSaved;
    }

    private String readFileContent(byte[] file) {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file)));
        StringBuilder contentBuilder = new StringBuilder();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
