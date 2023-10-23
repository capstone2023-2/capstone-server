package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Collection;
import com.capstone.demo.utils.DtoConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class CollectionResponseDto {

    private Long collectionId;
    private String name;
    private String author;
    private List<ThreadResponseDto> threads;

    public static CollectionResponseDto of(Collection collection){
        return CollectionResponseDto.builder()
                .collectionId(collection.getCollectionId())
                .name(collection.getName())
                .author(collection.getAuthor().getUsername())
                .threads(DtoConverter.convertThreadsToResponseDto(collection.getThreads()))
                .build();
    }
}
