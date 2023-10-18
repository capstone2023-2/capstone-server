package com.capstone.demo.controller;

import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "포스트 투표", description = "특정 포스트에 대해 1 또는 -1로 투표합니다.")
    @PutMapping("/posts/{postId}/votes")
    public ResponseEntity<BaseResponseDto> updateVote(@RequestBody int upOrDown,
                                                      @PathVariable Long postId,
                                                      Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "vote successfully updated!",
                        voteService.updateVote(upOrDown, postId, authentication.getName())), HttpStatus.CREATED);
    }
}
