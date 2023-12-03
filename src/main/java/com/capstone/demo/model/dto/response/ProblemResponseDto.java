package com.capstone.demo.model.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemResponseDto {

    private Integer id;
    private String topic;
    private String question;
    private String answer;
    private String audio;
}
