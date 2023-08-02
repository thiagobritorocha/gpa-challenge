package com.upload.file.api.domain.ports.inbound;

import com.upload.file.api.domain.entity.TransactionFile;

public interface UploadTransactionFileUseCasePort {
    TransactionFile execute(TransactionFile transactionFile);
}
