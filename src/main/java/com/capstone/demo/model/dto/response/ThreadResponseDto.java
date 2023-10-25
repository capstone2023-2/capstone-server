package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.utils.DtoConverter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ThreadResponseDto {

    private Long threadId;
    private String name;
    private String description;
    private String author;
    private List<PostResponseDto> posts;
    private Long collectionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ThreadResponseDto of(Thread thread){
        return ThreadResponseDto.builder()
                .threadId(thread.getThreadId())
                .name(thread.getName())
                .description(thread.getDescription())
                .author(thread.getAuthor().getUsername())
                .posts(DtoConverter.convertPostsToResponseDto(thread.getPosts()))
                .createdAt(thread.getCreatedAt())
                .updatedAt(thread.getUpdatedAt())
                .build();
    }
}
