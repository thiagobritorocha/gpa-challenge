package com.upload.file.api.adapter.outbound.repository.transactionfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.entity.enums.TransactionFileStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindTransactionFileAdapterTest {
    @Mock private TransferFileRepository transferFileRepository;

    @InjectMocks private FindTransactionFileAdapter findTransactionFileAdapter;

    private TransactionFile transactionFile;
    private TransactionFileEntity transactionFileEntity;

    @BeforeEach
    public void setUp() {

        this.transactionFile =
                TransactionFile.builder()
                        .fileName("Teste")
                        .transactionFileStatus(TransactionFileStatus.WAITING)
                        .startProcessTime(LocalDateTime.parse("2023-08-30T19:34:50.63"))
                        .endProcessTime(LocalDateTime.parse("2023-08-30T19:34:50.63"))
                        .fileData("".getBytes())
                        .build();
        this.transactionFileEntity = transactionFile.toEntity();
    }

    @Test
    void Should_not_find_transaction_file_by_name() {
        when(transferFileRepository.findByFileName("CNAB.TXT")).thenReturn(null);
        assertEquals(findTransactionFileAdapter.execute("CNAB.TXT"), null);
    }

    @Test
    void Should_find_transaction_file_by_name() {
        when(transferFileRepository.findByFileName("CNAB.TXT")).thenReturn(transactionFileEntity);
        assertEquals(findTransactionFileAdapter.execute("CNAB.TXT"), transactionFile);
    }
}
