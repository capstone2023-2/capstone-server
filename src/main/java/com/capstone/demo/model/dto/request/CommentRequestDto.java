package com.capstone.demo.model.dto.request;

import com.capstone.demo.model.dto.response.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDto {

    private String content;
    private Long userId;
    private Long questionId;
    private Long answerId;

    @Getter
    @Builder
    public static class AnswerRequest{
        private String content;
        private Long userId;
        private Long answerId;

        public static CommentRequestDto.AnswerRequest of(CommentRequestDto commentRequestDto){
            return AnswerRequest.builder()
                    .content(commentRequestDto.getContent())
                    .userId(commentRequestDto.getUserId())
                    .answerId(commentRequestDto.getAnswerId())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class QuestionRequest{
        private String content;
        private Long userId;
        private Long questionId;

        public static CommentRequestDto.QuestionRequest of(CommentRequestDto commentRequestDto){
            return QuestionRequest.builder()
                    .content(commentRequestDto.getContent())
                    .userId(commentRequestDto.getUserId())
                    .questionId(commentRequestDto.getQuestionId())
                    .build();
        }
    }
}
