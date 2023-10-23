package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.ThreadRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.model.dto.response.ThreadResponseDto;
import com.capstone.demo.service.ThreadService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "스레드 기능", description = "ThreadController")
public class ThreadController {

    private final ThreadService threadService;

    @Operation(summary = "스레드 생성", description = "새로운 스레드를 생성합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "스레드 생성 성공",
                    content = @Content(schema = @Schema(implementation = ThreadResponseDto.class)))
    }, tags = "스레드 기능")
    @PostMapping("/threads")
    public ResponseEntity<BaseResponseDto<ThreadResponseDto>> addThread(@RequestBody ThreadRequestDto threadRequestDto, Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        authentication.getName() +"님의 thread successfully added!",
                        threadService.addThread(threadRequestDto, authentication.getName())), HttpStatus.CREATED);
    }

    @Operation(summary = "스레드 조회", description = "특정 스레드를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "스레드 조회 성공",
                    content = @Content(schema = @Schema(implementation = ThreadResponseDto.class)))
    }, tags = "스레드 기능")
    @GetMapping("/threads/{id}")
    public ResponseEntity<BaseResponseDto<ThreadResponseDto>> getThread(@PathVariable Long id){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "the thread data successfully received!",
                        threadService.getThread(id)), HttpStatus.OK);
    }

    @Operation(summary = "컬렉션을 통해 스레드 조회", description = "특정 컬렉션의 특정 스레드를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "컬렉션을 거쳐 스레드 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponseDto.class)))
    }, tags = "스레드 기능")
    @GetMapping("/collections/{collectionId}/threads/{threadId}")
    public ResponseEntity<BaseResponseDto<ThreadResponseDto>> getThread(@PathVariable Long collectionId,
                                                     @PathVariable Long threadId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "the thread data successfully received!",
                        threadService.getThread(collectionId, threadId)), HttpStatus.OK);
    }

    @Operation(summary = "모든 스레드 조회", description = "생성된 모든 스레드를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "모든 스레드 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ThreadResponseDto.class))))
    }, tags = "스레드 기능")
    @GetMapping("/threads")
    public ResponseEntity<BaseResponseDto<List<ThreadResponseDto>>> getThreads(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all threads data successfully received!",
                        threadService.getThreads()), HttpStatus.OK);
    }

    @Operation(summary = "스레드 삭제", description = "특정 스레드를 삭제합니다.", tags = "스레드 기능")
    @DeleteMapping("/threads/{threadId}")
    public ResponseEntity<BaseResponseDto> deleteThreadById(@PathVariable Long threadId,
                                                            Authentication authentication) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "the thread successfully deleted",
                        threadService.deleteThread(threadId, authentication.getName())), HttpStatus.OK);
    }
}
