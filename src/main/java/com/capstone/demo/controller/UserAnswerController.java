package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.UserAnswerRequestDto;
import com.capstone.demo.model.dto.response.UserAnswerResponseDto;
import com.capstone.demo.service.UserAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-answer")
@RequiredArgsConstructor
@Tag(name = "사용자 답변 기능", description = "사용자 답변 작성 및 조회")
public class UserAnswerController {

    private final UserAnswerService userAnswerService;

    @Operation(summary = "질문별 사용자 답변 작성", description = "로그인한 사용자가 특정 주제의 특정 질문에 대해 사용자 답변을 작성합니다.", parameters = {}, responses = {
            @ApiResponse(responseCode = "200", description = "사용자 답변 작성 완료, 답변 정보 반환",
                    content = @Content(schema = @Schema(implementation = UserAnswerResponseDto.class)))
    }, tags = "사용자 답변 기능")
    @PostMapping("/{topic}/{questionId}")
    public ResponseEntity<?> createUserAnswer(@RequestBody UserAnswerRequestDto answerRequest,
                                              @PathVariable String topic,
                                              @PathVariable Integer questionId, Authentication authentication) {
        return ResponseEntity.ok(
                userAnswerService.createUserAnswer(answerRequest, topic, questionId, authentication.getName()));
    }

    @Operation(summary = "질문별 사용자 답변 히스토리 조회", description = "로그인한 사용자가 특정 주제의 특정 질문에 대해 자신의 사용자 답변 히스토리를 조회합니다..", responses = {
            @ApiResponse(responseCode = "200", description = "사용자 답변 히스토리 반환",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserAnswerResponseDto.class))))
    }, tags = "사용자 답변 기능")
    @GetMapping("/{topic}/{questionId}")
    public ResponseEntity<?> getUserAnswer(@PathVariable String topic, @PathVariable Integer questionId,
                                           Authentication authentication) {
        return ResponseEntity.ok(userAnswerService.getUserAnswer(topic, questionId, authentication.getName()));
    }
}