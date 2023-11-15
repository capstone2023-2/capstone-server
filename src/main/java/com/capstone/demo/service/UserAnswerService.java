package com.capstone.demo.service;

import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.domain.UserAnswer;
import com.capstone.demo.model.dto.request.UserAnswerRequestDto;
import com.capstone.demo.model.dto.response.UserAnswerResponseDto;
import com.capstone.demo.repository.UserAnswerRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final UserService userService;

    public UserAnswerResponseDto createUserAnswer(UserAnswerRequestDto requestDto, String topic, Integer questionId,
                                                  String email) {

        User findUser = userService.findByEmail(email);

        UserAnswer userAnswer = UserAnswer.builder()
                .author(findUser)
                .topic(topic)
                .questionId(questionId)
                .answer(requestDto.getAnswer())
                .build();

        findUser.getUserAnswers().add(userAnswer);
        userAnswerRepository.save(userAnswer);

        return UserAnswerResponseDto.of(userAnswer);
    }

    public List<UserAnswerResponseDto> getUserAnswer(String topic, Integer questionId, String email) {

        User findUser = userService.findByEmail(email);
        List<UserAnswer> userAnswers = findUser.getUserAnswers();

        return userAnswers.stream()
                .filter(userAnswer -> userAnswer.getTopic().equals(topic)
                        && userAnswer.getQuestionId().equals(questionId))
                .map(UserAnswerResponseDto::of)
                .collect(Collectors.toList());
    }
}