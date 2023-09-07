package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Answer;
import com.capstone.demo.utils.DtoConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AnswerResponseDto {

    private String content;
    private List<CommentResponseDto> comments;

    public static AnswerResponseDto of(Answer answer){
        return AnswerResponseDto.builder()
                .content(answer.getContent())
                .comments(DtoConverter.convertCommentsToResponseDto(answer.getComments()))
                .build();
    }
}
