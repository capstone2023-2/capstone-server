package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.CommentRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "포스트 댓글 작성", description = "특정 포스트에 댓글을 작성합니다.")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<BaseResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                         @PathVariable("postId") Long postId,
                                                         Authentication authentication) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "comment successfully created!",
                        commentService.createComment(commentRequestDto, postId, authentication.getName())), HttpStatus.CREATED);
    }

    @Operation(summary = "포스트 댓글 조회", description = "특정 포스트의 모든 댓글을 조회합니다.")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<BaseResponseDto> getCommentsOfPost(@PathVariable("postId") Long postId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all comments data successfully received!",
                        commentService.getCommentsOfPost(postId)), HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정", description = "특정 댓글의 내용을 수정합니다.")
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<BaseResponseDto> updateCommentById(@PathVariable Long commentId,
                                                             String updateContent){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "comment successfully updated",
                        commentService.updateComment(commentId, updateContent)), HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<BaseResponseDto> deleteCommentById(@PathVariable("commentId") Long commentId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "comment successfully deleted",
                        commentService.deleteComment(commentId)), HttpStatus.OK);
    }
}
