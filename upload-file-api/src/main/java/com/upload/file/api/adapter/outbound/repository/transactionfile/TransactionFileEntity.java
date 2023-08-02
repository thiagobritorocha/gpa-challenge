package com.upload.file.api.adapter.outbound.repository.transactionfile;

import com.upload.file.api.domain.entity.enums.TransactionFileStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "transaction_files")
@Builder
@AllArgsConstructor
@Setter
@Getter
public class TransactionFileEntity {
    public TransactionFileEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column private String fileName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionFileStatus transactionFileStatus;

    @Column @CreationTimestamp private LocalDateTime startProcessTime;

    @Column private LocalDateTime endProcessTime;

    @Column(length = 5000)
    private byte[] fileData;
}
