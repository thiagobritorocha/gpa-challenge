package com.upload.file.api.adapter.inbound.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.entity.enums.TransactionFileStatus;
import com.upload.file.api.domain.usecase.UploadTransactionFileUseCase;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TransactionFileController.class)
public class TransactionFileControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean UploadTransactionFileUseCase uploadTransactionFileUseCase;

    private MockMultipartFile file;
    private TransactionFile transactionFile;

    @BeforeEach
    public void setUp() throws IOException {
        this.file =
                new MockMultipartFile(
                        "file",
                        "test-file.txt",
                        "text/plain",
                        "Conteúdo do arquivo de teste".getBytes());

        this.transactionFile =
                TransactionFile.builder()
                        .id(UUID.randomUUID())
                        .fileName(file.getOriginalFilename())
                        .transactionFileStatus(TransactionFileStatus.WAITING)
                        .startProcessTime(LocalDateTime.parse("2023-08-30T19:34:50.63"))
                        .endProcessTime(LocalDateTime.parse("2023-08-30T19:34:50.63"))
                        .fileData(file.getBytes())
                        .build();
    }

    @Test
    void Should_upload_transaction_file_success() throws Exception {

        when(uploadTransactionFileUseCase.execute(TransactionFile.parse(file)))
                .thenReturn(transactionFile);

        this.mockMvc
                .perform(multipart("/transaction-files").file(file))
                .andExpect(status().isCreated());
    }
}
