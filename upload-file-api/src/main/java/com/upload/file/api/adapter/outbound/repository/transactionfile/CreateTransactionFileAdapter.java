package com.upload.file.api.adapter.outbound.repository.transactionfile;

import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.ports.outbound.CreateTransactionFileAdapterPort;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CreateTransactionFileAdapter implements CreateTransactionFileAdapterPort {

    private final TransferFileRepository transferFileRepository;

    public CreateTransactionFileAdapter(TransferFileRepository transferFileRepository) {
        this.transferFileRepository = transferFileRepository;
    }

    @Override
    @Transactional
    public TransactionFile execute(TransactionFile transactionFile) {
        TransactionFileEntity transactionFileEntity = transactionFile.toEntity();

        var transactionFileEntityResult = transferFileRepository.save(transactionFileEntity);
        return TransactionFile.fromEntity(transactionFileEntityResult);
    }
}
