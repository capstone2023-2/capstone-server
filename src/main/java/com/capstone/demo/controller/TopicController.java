package com.capstone.demo.controller;

import com.capstone.demo.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
@Tag(name = "전체 Topic 불러오기 기능", description = "전체 Topic 제목을 조회")
public class TopicController {

    private final TopicService topicService;

    @Operation(summary = "전체 Topic 제목들을 조회", description = "전체 Topic 제목들을 불러옵니다."
    , tags = "전체 Topic 불러오기 기능")
    @GetMapping("/list")
    public ResponseEntity<?> getTopics(){
        return ResponseEntity.ok(topicService.getTopics());
    }
}