package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.ForumRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.model.dto.response.ForumResponseDto;
import com.capstone.demo.service.ForumService;
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
@RequestMapping("/api/v1/forums")
@RequiredArgsConstructor
@Tag(name = "오픈포럼 기능", description = "ForumController")
public class ForumController {

    private final ForumService forumService;

    @Operation(summary = "오픈 포럼 게시글 작성", description = "오픈 포럼에 게시글을 작성합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "오픈 포럼 작성 성공",
                    content = @Content(schema = @Schema(implementation = ForumResponseDto.class)))
    }, tags = "오픈포럼 기능")
    @PostMapping
    public ResponseEntity<BaseResponseDto<ForumResponseDto>> createForum(@RequestBody ForumRequestDto forumRequestDto,
                                                                         Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto<ForumResponseDto>(
                        HttpStatus.CREATED.value(),
                        "오픈 포럼 작성 완료",
                        forumService.createForum(forumRequestDto, authentication.getName())
                ), HttpStatus.CREATED);
    }

    @Operation(summary = "모든 오픈포럼 게시글 조회", description = "모든 오픈포럼 게시글을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "모든 오픈포럼 게시글 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ForumResponseDto.class))))
    }, tags = "오픈포럼 기능")
    @GetMapping
    public ResponseEntity<BaseResponseDto<List<ForumResponseDto>>> getForums(){

        return new ResponseEntity<>(
                new BaseResponseDto<List<ForumResponseDto>>(
                        HttpStatus.OK.value(),
                        "모든 오픈포럼 게시글을 조회 완료",
                        forumService.getForums()
                ), HttpStatus.OK);
    }

    @Operation(summary = "오픈 포럼 게시글 한 개 조회", description = "한 개의 오픈포럼 게시글을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "오픈 포럼 게시글 한 개 조회 성공",
                    content = @Content(schema = @Schema(implementation = ForumResponseDto.class)))
    }, tags = "오픈포럼 기능")
    @GetMapping("/{forumId}")
    public ResponseEntity<BaseResponseDto<ForumResponseDto>> getForum(@PathVariable Long forumId){

        return new ResponseEntity<>(
                new BaseResponseDto<ForumResponseDto>(
                        HttpStatus.OK.value(),
                        "오픈포럼 게시글 조회 완료",
                        forumService.getForum(forumId)
                ), HttpStatus.OK);
    }

    @Operation(summary = "오픈포럼 수정", description = "오픈포럼 게시글을 수정합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "오픈포럼 게시글 수정 성공",
                    content = @Content(schema = @Schema(implementation = ForumResponseDto.class)))
    }, tags = "오픈포럼 기능")
    @PatchMapping("/{forumId}/edit")
    public ResponseEntity<BaseResponseDto<ForumResponseDto>> updateForum(@RequestBody String updateContent,
                                                                       @PathVariable Long forumId,
                                                                       Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto<ForumResponseDto>(
                        HttpStatus.OK.value(),
                        "오픈포럼 게시글 수정 완료",
                        forumService.updateForum(forumId, updateContent, authentication.getName())
                ), HttpStatus.OK);
    }

    @Operation(summary = "오픈포럼 삭제", description = "오픈포럼 게시글을 삭제합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "오픈포럼 게시글 삭제 완료",
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    }, tags = "오픈포럼 기능")
    @DeleteMapping("/{forumId}")
    public ResponseEntity<BaseResponseDto<Boolean>> deleteForum(@PathVariable Long forumId,
                                                                Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto<Boolean>(
                        HttpStatus.OK.value(),
                        "오픈 포럼 게시글 삭제 완료",
                        forumService.deleteForum(forumId, authentication.getName())
                ), HttpStatus.OK);
    }
}