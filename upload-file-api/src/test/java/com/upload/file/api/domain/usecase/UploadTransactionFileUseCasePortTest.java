package com.upload.file.api.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.upload.file.api.adapter.outbound.producer.FileContentKafkaProducer;
import com.upload.file.api.adapter.outbound.repository.transactionfile.CreateTransactionFileAdapter;
import com.upload.file.api.adapter.outbound.repository.transactionfile.FindTransactionFileAdapter;
import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.entity.enums.TransactionFileStatus;
import com.upload.file.api.domain.exception.BusinessException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UploadTransactionFileUseCasePortTest {

    @InjectMocks private UploadTransactionFileUseCase uploadTransactionFileUseCase;
    @Mock private FindTransactionFileAdapter findTransactionFileAdapter;
    @Mock private CreateTransactionFileAdapter createTransactionFileAdapter;
    @Mock private FileContentKafkaProducer fileContentKafkaProducer;

    private TransactionFile transactionFile;

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
    }

    @Test
    void Should_return_transaction_file_with_status_waiting() {
        when(findTransactionFileAdapter.execute("Teste")).thenReturn(null);
        when(createTransactionFileAdapter.execute(transactionFile)).thenReturn(transactionFile);
        assertEquals(
                uploadTransactionFileUseCase.execute(transactionFile).getTransactionFileStatus(),
                transactionFile.getTransactionFileStatus());
    }

    @Test
    void Should_throws_conflictException() {
        when(findTransactionFileAdapter.execute("Teste")).thenReturn(transactionFile);
        assertThrows(
                BusinessException.class,
                () -> {
                    uploadTransactionFileUseCase.execute(transactionFile);
                });
    }
}
