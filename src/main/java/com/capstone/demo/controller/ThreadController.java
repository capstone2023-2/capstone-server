package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.ThreadRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.ThreadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/threads")
public class ThreadController {

    private final ThreadService threadService;

    @Operation(summary = "스레드 생성", description = "새로운 스레드를 생성합니다.")
    @PostMapping
    public ResponseEntity<BaseResponseDto> addThread(@RequestBody ThreadRequestDto threadRequestDto, Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        authentication.getName() +"님의 thread successfully added!",
                        threadService.addThread(threadRequestDto, authentication.getName())), HttpStatus.CREATED);
    }

    @Operation(summary = "스레드 조회", description = "특정 스레드를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getThread(@PathVariable Long id){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all threads data successfully received!",
                        threadService.getThread(id)), HttpStatus.OK);
    }

    @Operation(summary = "모든 스레드 조회", description = "생성된 모든 스레드를 조회합니다.")
    @GetMapping
    public ResponseEntity<BaseResponseDto> getThreads(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all threads data successfully received!",
                        threadService.getThreads()), HttpStatus.OK);
    }

    @Operation(summary = "스레드 삭제", description = "특정 스레드를 삭제합니다.")
    @DeleteMapping("/{threadId}")
    public ResponseEntity<BaseResponseDto> deleteThreadById(@PathVariable Long threadId,
                                                            Authentication authentication) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "thread successfully deleted",
                        threadService.deleteThread(threadId, authentication.getName())), HttpStatus.OK);
    }
}
