package com.upload.file.api.domain.exception;

import java.util.List;
import java.util.Map;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private int statusCode;

    private String statusMessage;

    private List<Map<String, Object>> errors;
}
