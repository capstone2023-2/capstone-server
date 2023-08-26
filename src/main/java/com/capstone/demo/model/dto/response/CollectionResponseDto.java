package com.capstone.demo.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CollectionResponseDto {

    private String name;
    private String author;
    private List<Thread> threads;
}
