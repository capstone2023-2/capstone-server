package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Post;
import com.capstone.demo.utils.DtoConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponseDto {

    private String title;
    private String content;
    private int views;
    private List<CommentResponseDto> comments;

    public static PostResponseDto of(Post post){
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .comments(DtoConverter.convertCommentsToResponseDto(post.getComments()))
                .build();
    }
}
