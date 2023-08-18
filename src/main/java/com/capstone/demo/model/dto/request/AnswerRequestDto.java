package com.capstone.demo.model.dto.request;

import lombok.Getter;

@Getter
public class AnswerRequestDto {

    private String content;
    private Long userId;
    private Long questionId;
}
