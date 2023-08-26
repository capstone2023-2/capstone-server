package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.utils.DtoConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ThreadResponseDto {

    private String name;
    private String description;
    private String author;
    private List<PostResponseDto> posts;

    public static ThreadResponseDto of(Thread thread){
        return ThreadResponseDto.builder()
                .name(thread.getName())
                .description(thread.getDescription())
                .author(thread.getAuthor().getUsername())
                .posts(DtoConverter.convertPostsToResponseDto(thread.getPosts()))
                .build();
    }
}
