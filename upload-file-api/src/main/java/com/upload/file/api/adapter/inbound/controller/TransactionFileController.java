package com.upload.file.api.adapter.inbound.controller;

import com.upload.file.api.domain.entity.TransactionFile;
import com.upload.file.api.domain.ports.inbound.UploadTransactionFileUseCasePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/transaction-files")
@CrossOrigin(origins = "*")
public class TransactionFileController {

    private final UploadTransactionFileUseCasePort uploadTransactionFileUseCasePort;

    public TransactionFileController(
            UploadTransactionFileUseCasePort uploadTransactionFileUseCasePort) {
        this.uploadTransactionFileUseCasePort = uploadTransactionFileUseCasePort;
    }

    @Operation(summary = "Upload transaction file")
    // TODO Adicionar outros response e exceptionHandler
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "422", description = "Arquivo com esse nome já existe"),
            @ApiResponse(responseCode = "422", description = "Arquivo vazio não permitido")
        }
    )
    @PostMapping()
    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        TransactionFile result =
                uploadTransactionFileUseCasePort.execute(TransactionFile.parse(file));
        return ResponseEntity.created(
                        URI.create(
                                String.format("/transaction-files/%s", result.getId().toString())))
                .build();
    }
}
