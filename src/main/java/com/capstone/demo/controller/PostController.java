package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.PostRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createPost(@RequestBody PostRequestDto postRequestDto){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "post successfully registered!",
                        postService.createPost(postRequestDto)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getPosts(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all posts data successfully received!",
                        postService.getPosts()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deletePostById(@PathVariable Long id) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "post successfully deleted",
                        postService.deletePost(id)), HttpStatus.OK);
    }
}
