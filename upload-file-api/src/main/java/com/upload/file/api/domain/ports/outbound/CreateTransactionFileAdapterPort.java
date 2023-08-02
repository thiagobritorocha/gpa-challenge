package com.upload.file.api.domain.ports.outbound;

import com.upload.file.api.domain.entity.TransactionFile;

public interface CreateTransactionFileAdapterPort {

    TransactionFile execute(TransactionFile transactionFile);
}
