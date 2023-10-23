package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.utils.DtoConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    public static ThreadResponseDto of(Thread thread){
        return ThreadResponseDto.builder()
                .threadId(thread.getThreadId())
                .name(thread.getName())
                .description(thread.getDescription())
                .author(thread.getAuthor().getUsername())
                .posts(DtoConverter.convertPostsToResponseDto(thread.getPosts()))
                .build();
    }
}
