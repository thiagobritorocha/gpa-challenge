package com.upload.file.api.adapter.outbound.repository.transactionfile;

import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.ports.outbound.FindTransactionFileAdapterPort;
import org.springframework.stereotype.Component;

@Component
public class FindTransactionFileAdapter implements FindTransactionFileAdapterPort {

    private final TransferFileRepository transferFileRepository;

    public FindTransactionFileAdapter(TransferFileRepository transferFileRepository) {
        this.transferFileRepository = transferFileRepository;
    }

    @Override
    public TransactionFile execute(String name) {
        TransactionFileEntity transactionFileEntityResult =
                transferFileRepository.findByFileName(name);
        if (transactionFileEntityResult == null) {
            return null;
        }
        return TransactionFile.fromEntity(transactionFileEntityResult);
    }
}
