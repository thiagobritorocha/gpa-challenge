package com.upload.file.api.domain.entity;

import com.upload.file.api.adapter.outbound.repository.transactionfile.TransactionFileEntity;
import com.upload.file.api.domain.entity.enums.TransactionFileStatus;
import com.upload.file.api.domain.exception.Error;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionFile {

    private UUID id;

    private String fileName;

    private TransactionFileStatus transactionFileStatus;

    private LocalDateTime startProcessTime;

    private LocalDateTime endProcessTime;
    //TODO Salvar arquivo no bucket S3
    private byte[] fileData;

    public static TransactionFile parse(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw Error.NOT_EMPTY.toBusinessException();
        }
        return TransactionFile.builder()
                .fileName(file.getOriginalFilename())
                .transactionFileStatus(TransactionFileStatus.WAITING)
                .fileData(file.getBytes())
                .build();
    }

    public static TransactionFile fromEntity(TransactionFileEntity transactionFileEntity) {
        return TransactionFile.builder()
                .id(transactionFileEntity.getId())
                .fileName(transactionFileEntity.getFileName())
                .transactionFileStatus(transactionFileEntity.getTransactionFileStatus())
                .startProcessTime(transactionFileEntity.getStartProcessTime())
                .endProcessTime(transactionFileEntity.getEndProcessTime())
                .fileData(transactionFileEntity.getFileData())
                .build();
    }

    public TransactionFileEntity toEntity() {
        return TransactionFileEntity.builder()
                .fileName(this.getFileName())
                .transactionFileStatus(this.getTransactionFileStatus())
                .startProcessTime(this.getStartProcessTime())
                .endProcessTime(this.getEndProcessTime())
                .fileData(this.getFileData())
                .build();
    }
}
