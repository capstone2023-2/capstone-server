package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.UserAnswer;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAnswerResponseDto {

    private final String answer;
    private final LocalDateTime createdAt;

    public static UserAnswerResponseDto of(UserAnswer userAnswer){
        return UserAnswerResponseDto.builder()
                .answer(userAnswer.getAnswer())
                .createdAt(userAnswer.getCreatedAt())
                .build();
    }
}