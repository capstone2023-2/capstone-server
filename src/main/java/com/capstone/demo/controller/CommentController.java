package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.CommentRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.model.dto.response.CommentResponseDto;
import com.capstone.demo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "댓글 기능", description = "CommentController")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "포스트 댓글 작성", description = "특정 포스트에 댓글을 작성합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공, 댓글 정보 반환",
                    content = @Content(schema = @Schema(implementation = CommentResponseDto.class)))
    }, tags = "댓글 기능")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<BaseResponseDto<CommentResponseDto>> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                                             @PathVariable("postId") Long postId,
                                                                             Authentication authentication) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "comment successfully created!",
                        commentService.createComment(commentRequestDto, postId, authentication.getName())), HttpStatus.CREATED);
    }

    @Operation(summary = "포스트 댓글 조회", description = "특정 포스트의 모든 댓글을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공",
                    content =  @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseDto.class))))
    }, tags = "댓글 기능")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<BaseResponseDto<List<CommentResponseDto>>> getCommentsOfPost(@PathVariable("postId") Long postId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all comments data successfully received!",
                        commentService.getCommentsOfPost(postId)), HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정", description = "특정 댓글의 내용을 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공, 댓글 정보 반환",
                    content = @Content(schema = @Schema(implementation = CommentResponseDto.class)))
    },tags = "댓글 기능")
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<BaseResponseDto<CommentResponseDto>> updateCommentById(@PathVariable Long commentId,
                                                                                 @RequestBody String updateContent,
                                                                                 Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "comment successfully updated",
                        commentService.updateComment(commentId, updateContent, authentication.getName())), HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.", tags = "댓글 기능")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<BaseResponseDto> deleteCommentById(@PathVariable("commentId") Long commentId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "comment successfully deleted",
                        commentService.deleteComment(commentId)), HttpStatus.OK);
    }
}
