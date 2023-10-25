package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Forum;
import com.capstone.demo.utils.DtoConverter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ForumResponseDto {

    private Long forumId;
    private String username;
    private String title;
    private String content;
    private Integer bookmarkCount;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ForumResponseDto of(Forum forum){
        return ForumResponseDto.builder()
                .forumId(forum.getForumId())
                .username(forum.getAuthor()
                        .getUsername())
                .title(forum.getTitle())
                .content(forum.getContent())
                .bookmarkCount(forum.getBookmarkCount())
                .comments(DtoConverter.convertCommentsToResponseDto(forum.getComments()))
                .createdAt(forum.getCreatedAt())
                .updatedAt(forum.getUpdatedAt())
                .build();
    }
}