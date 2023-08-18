package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.Question;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.utils.DtoConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserResponseDto {

    private String email;
    private String username;
    private List<QuestionResponseDto> questions;
    private List<AnswerResponseDto> answers;
    private List<CommentResponseDto> comments;

    public static UserResponseDto of(User user){
        return UserResponseDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .questions(DtoConverter.convertQuestionsToResponseDto(user.getQuestions()))
                .answers(DtoConverter.convertAnswersToResponseDto(user.getAnswers()))
                .comments(DtoConverter.convertCommentsToResponseDto(user.getComments()))
                .build();
    }

}
