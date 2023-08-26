package com.capstone.demo.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequestDto {

    private String title;
    private String content;
    private Long userId;
    private Long threadId;
}
