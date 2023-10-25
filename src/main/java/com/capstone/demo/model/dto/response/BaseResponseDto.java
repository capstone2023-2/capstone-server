package com.capstone.demo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "공통 응답")
public class BaseResponseDto<T> {

    @Schema(description = "Http 응답 코드")
    private int status;
    @Schema(description = "응답 메시지")
    private String message;
    @Schema(description = "응답 데이터")
    private Object data;
}