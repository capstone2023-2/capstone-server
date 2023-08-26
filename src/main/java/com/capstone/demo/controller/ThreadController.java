package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.ThreadRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/threads")
public class ThreadController {

    private final ThreadService threadService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createThread(@RequestBody ThreadRequestDto threadRequestDto){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "thread successfully added!",
                        threadService.createThread(threadRequestDto)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getThreads(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all threads data successfully received!",
                        threadService.getThreads()), HttpStatus.OK);
    }
}
