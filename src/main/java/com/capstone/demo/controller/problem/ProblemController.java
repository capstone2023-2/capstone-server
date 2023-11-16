package com.capstone.demo.controller.problem;

import com.capstone.demo.service.problem.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
@Tag(name = "주제별 면접 문제 기능", description = "주제별 면접 문제 목록 조회")
public class ProblemController {

    private final ProblemService problemService;

    @Operation(summary = "Algorithm 주제의 모든 문제 조회", description = "Algorithm 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping("/algorithm")
    public ResponseEntity<?> getAlgorithmProblems() {
        return ResponseEntity.ok(problemService.getAlgorithmProblems());
    }

    @Operation(summary = "Database 주제의 모든 문제 조회", description = "Database 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping("/database")
    public ResponseEntity<?> getDatabaseProblems() {
        return ResponseEntity.ok(problemService.getDatabaseProblems());
    }

    @Operation(summary = "DataStructure 주제의 모든 문제 조회", description = "DataStructure 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping("/datastructure")
    public ResponseEntity<?> getDataStructureProblems() {
        return ResponseEntity.ok(problemService.getDataStructureProblems());
    }

    @Operation(summary = "Java 주제의 모든 문제 조회", description = "Java 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping("/java")
    public ResponseEntity<?> getJavaProblems() {
        return ResponseEntity.ok(problemService.getJavaProblems());
    }

    @Operation(summary = "Javascript 주제의 모든 문제 조회", description = "Javascript 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping("/javascript")
    public ResponseEntity<?> getJavascriptProblems() {
        return ResponseEntity.ok(problemService.getJavascriptProblems());
    }

    @Operation(summary = "Network 주제의 모든 문제 조회", description = "Network 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping("/network")
    public ResponseEntity<?> getNetworkProblems() {
        return ResponseEntity.ok(problemService.getNetworkProblems());
    }

    @Operation(summary = "OperatingSystem 주제의 모든 문제 조회", description = "OperatingSystem 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping("/operatingsystem")
    public ResponseEntity<?> getOperatingSystemProblems() {
        return ResponseEntity.ok(problemService.getOperatingSystemProblems());
    }

    @Operation(summary = "Spring 주제의 모든 문제 조회", description = "Spring 주제의 모든 문제들을 불러옵니다."
            , tags = "주제별 면접 문제 기능")
    @GetMapping
    public ResponseEntity<?> getSpringProblems() {
        return ResponseEntity.ok(problemService.getSpringProblems());
    }
}