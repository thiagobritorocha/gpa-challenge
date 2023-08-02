package com.upload.file.api.domain.ports.outbound;

import com.upload.file.api.domain.entity.TransactionFile;

public interface FindTransactionFileAdapterPort {

    TransactionFile execute(String name);
}
