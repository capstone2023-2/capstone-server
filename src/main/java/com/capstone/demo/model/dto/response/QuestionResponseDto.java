package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Question;
import com.capstone.demo.utils.DtoConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionResponseDto {

    private String title;
    private String content;
    private int views;
    private List<AnswerResponseDto> answers;
    private List<CommentResponseDto> comments;

    public static QuestionResponseDto of(Question question){
        return QuestionResponseDto.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .views(question.getViews())
                .answers(DtoConverter.convertAnswersToResponseDto(question.getAnswers()))
                .comments(DtoConverter.convertCommentsToResponseDto(question.getComments()))
                .build();
    }
}
