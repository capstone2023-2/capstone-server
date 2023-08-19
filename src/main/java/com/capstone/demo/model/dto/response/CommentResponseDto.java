package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {

    private String content;
    private Long authorId;

    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .authorId(comment.getAuthor().getUserId())
                .build();
    }
}
