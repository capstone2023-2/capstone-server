package com.capstone.demo.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDto {

    private String content;
    private Long userId;
    private Long questionId;
    private Long answerId;
}
