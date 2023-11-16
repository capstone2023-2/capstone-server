package com.capstone.demo.controller.problem;

import com.capstone.demo.service.problem.DataStructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topics/datastructure")
@RequiredArgsConstructor
@Tag(name = "DataStructure 면접 문제 기능", description = "DataStructure 주제의 면접 문제 목록 조회")
public class DataStructureController {

    private final DataStructureService dataStructureService;

    @Operation(summary = "DataStructure 주제의 모든 문제 조회", description = "DataStructure 주제의 모든 문제들을 불러옵니다."
            , tags = "DataStructure 면접 문제 기능")
    @GetMapping
    public ResponseEntity<?> getProblems(){
        return ResponseEntity.ok(dataStructureService.getProblems());
    }
}