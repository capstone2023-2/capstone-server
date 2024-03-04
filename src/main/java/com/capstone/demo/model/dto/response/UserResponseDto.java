package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    private String email;
    private String username;
    private Long userId;
    private List<UserAnswerResponseDto> userAnswers;
    private LocalDateTime createdAt;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .userAnswers(user.getUserAnswers().stream()
                        .map(UserAnswerResponseDto::of)
                        .collect(Collectors.toList()))
                .createdAt(user.getCreatedAt())
                .build();
    }

}
