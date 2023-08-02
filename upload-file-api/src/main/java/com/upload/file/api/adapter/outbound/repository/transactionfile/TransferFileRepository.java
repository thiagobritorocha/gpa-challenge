package com.upload.file.api.adapter.outbound.repository.transactionfile;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferFileRepository extends JpaRepository<TransactionFileEntity, UUID> {

    TransactionFileEntity findByFileName(String name);
}
