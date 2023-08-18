package com.capstone.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponseDto {

    private int status;
    private String message;
    private Object data;
}