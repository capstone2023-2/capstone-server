package com.capstone.demo.controller.interview;

import com.capstone.demo.model.dto.response.ProblemResponseDto;
import com.capstone.demo.service.interview.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/interview")
@RequiredArgsConstructor
@Tag(name = "모의 면접 기능", description = "랜덤 면접 질문 제공")
public class InterviewController {

    private final InterviewService interviewService;

    @Operation(summary = "랜덤한 주제의 랜덤한 질문 제공", description = "랜덤한 질문 하나를 오디오 파일 경로와 함께 제공합니다",
            tags = "모의 면접 기능")
    @GetMapping
    public ResponseEntity<?> getRandomProblem() {
        ProblemResponseDto responseDto = interviewService.getRandomInterviewProblem();

        return ResponseEntity.ok().body(responseDto);
    }
}