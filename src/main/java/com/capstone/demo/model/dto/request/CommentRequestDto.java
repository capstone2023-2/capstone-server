package com.capstone.demo.model.dto.request;

import com.capstone.demo.model.dto.response.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDto {

    private String content;
    private Long userId;
    private Long postId;
}
