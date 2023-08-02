package com.upload.file.api.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    DATA_INTEGRITY_VIOLATION("Arquivo com esse nome já existe.", 1000),
    NOT_EMPTY("Arquivo vazio não permitido.", 1001);

    private final String message;
    private final int code;
    private final HttpStatus status;

    Error(String message, int code) {
        this.message = message;
        this.code = code;
        this.status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    Error(String message) {
        this.message = message;
        this.code = 0;
        this.status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public BusinessException toBusinessException() {
        return new BusinessException(String.format("%04d", code), message);
    }

    public BusinessException toBusinessException(Integer code) {
        return new BusinessException(status, String.format("%04d", code), message);
    }
}
