package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .authorId(comment.getAuthor().getUserId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
