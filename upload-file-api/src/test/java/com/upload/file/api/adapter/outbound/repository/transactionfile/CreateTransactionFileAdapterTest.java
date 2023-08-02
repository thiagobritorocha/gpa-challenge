package com.upload.file.api.adapter.outbound.repository.transactionfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.entity.enums.TransactionFileStatus;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionFileAdapterTest {

    @Mock private TransferFileRepository transferFileRepository;
    @InjectMocks private CreateTransactionFileAdapter createTransactionFileAdapter;

    private TransactionFile transactionFile;
    TransactionFileEntity transactionFileEntity;

    TransactionFileEntity transactionFileEntityResult;

    @BeforeEach
    public void setUp() {
        this.transactionFile =
                TransactionFile.builder()
                        .fileName("Teste")
                        .transactionFileStatus(TransactionFileStatus.WAITING)
                        .fileData("".getBytes())
                        .build();

        this.transactionFileEntity = transactionFile.toEntity();
        this.transactionFileEntityResult = transactionFile.toEntity();
        this.transactionFileEntityResult.setId(UUID.randomUUID());
    }

    @Test
    void Should_return_transaction_file_created_with_status_waiting() {
        when(transferFileRepository.save(any())).thenReturn(transactionFileEntityResult);
        assertEquals(
                createTransactionFileAdapter.execute(transactionFile).getId(),
                transactionFileEntityResult.getId());
    }
}
