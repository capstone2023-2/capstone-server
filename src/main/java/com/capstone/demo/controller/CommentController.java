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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {

        if (commentRequestDto.getAnswerId() != null)
            return new ResponseEntity<>(
                    new BaseResponseDto(
                            HttpStatus.CREATED.value(),
                            "comment to answer successfully created!",
                            commentService.createAnswerComment(CommentRequestDto.AnswerRequest.of(commentRequestDto))), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(
                    new BaseResponseDto(
                            HttpStatus.CREATED.value(),
                            "comment to question successfully created!",
                            commentService.createPostComment(CommentRequestDto.QuestionRequest.of(commentRequestDto))), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getAnswers() {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all comments data successfully received!",
                        commentService.getComments()), HttpStatus.OK);
    }
}
