package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.PostRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @Operation(summary = "특정 스레드에 포스트 추가", description = "특정 스레드에 새로운 포스트를 작성합니다.")
    @PostMapping("/threads/{threadId}/posts")
    public ResponseEntity<BaseResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                      @PathVariable Long threadId,
                                                      Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "post successfully registered!",
                        postService.createPost(postRequestDto, threadId, authentication.getName())), HttpStatus.CREATED);
    }

    @Operation(summary = "특정 스레드의 모든 포스트 조회", description = "특정 스레드에 등록된 모든 포스트를 조회합니다.")
    @GetMapping("/threads/{threadId}/posts")
    public ResponseEntity<BaseResponseDto> getPostsOfThread(@PathVariable Long threadId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all posts data successfully received!",
                        postService.getPostsOfThread(threadId)), HttpStatus.OK);
    }

    @Operation(summary = "포스트 정보 조회", description = "특정 포스트를 조회합니다.")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<BaseResponseDto> getPost(@PathVariable Long postId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all posts data successfully received!",
                        postService.getPost(postId)), HttpStatus.OK);
    }

    @Operation(summary = "모든 포스트 조회", description = "작성된 모든 포스트를 조회합니다.")
    @GetMapping("/posts")
    public ResponseEntity<BaseResponseDto> getPosts(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all posts data successfully received!",
                        postService.getPosts()), HttpStatus.OK);
    }

    @Operation(summary = "포스트 삭제", description = "특정 포스트를 삭제합니다.")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<BaseResponseDto> deletePostById(@PathVariable Long postId,
                                                          Authentication authentication) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "post successfully deleted",
                        postService.deletePost(postId, authentication.getName())), HttpStatus.OK);
    }
}
