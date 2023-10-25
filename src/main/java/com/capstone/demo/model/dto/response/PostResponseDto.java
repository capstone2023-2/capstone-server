package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.Vote;
import com.capstone.demo.utils.DtoConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostResponseDto {

    private Long postId;
    private String username;
    private String title;
    private String content;
    private Long threadId;
    private Integer views;
    private Integer bookmarkCount;
    private List<CommentResponseDto> comments;
    private Integer voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponseDto of(Post post){
        int voteCount = 0;
        for(Vote v: post.getVotes())    voteCount += v.getUpOrDown();

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .username(post.getAuthor()
                            .getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .threadId(post.getThread()
                            .getThreadId())
                .views(post.getViews())
                .bookmarkCount(post.getBookmarkCount())
                .comments(DtoConverter.convertCommentsToResponseDto(post.getComments()))
                .voteCount(voteCount)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}