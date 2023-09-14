package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.CommentRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{post-id}/comments")
    public ResponseEntity<BaseResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                         @PathVariable("post-id") Long postId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "comment successfully created!",
                        commentService.createComment(commentRequestDto, postId)), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{post-id}/comments")
    public ResponseEntity<BaseResponseDto> getCommentsOfPost(@PathVariable("post-id") Long postId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all comments data successfully received!",
                        commentService.getCommentsOfPost(postId)), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{post-id}/comments/{comment-id}")
    public ResponseEntity<BaseResponseDto> deleteCommentById(@PathVariable("post-id") Long postId,
                                                             @PathVariable("comment-id") Long commentId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "comment successfully deleted",
                        commentService.deleteComment(postId, commentId)), HttpStatus.OK);
    }
}
