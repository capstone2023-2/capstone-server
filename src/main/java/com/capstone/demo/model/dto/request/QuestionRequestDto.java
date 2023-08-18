package com.capstone.demo.model.dto.request;

import lombok.Getter;

@Getter
public class QuestionRequestDto {

    private String title;
    private String content;
    private Long userId;
}
