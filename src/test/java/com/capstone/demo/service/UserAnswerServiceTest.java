package com.capstone.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.capstone.demo.model.domain.Role;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.domain.UserAnswer;
import com.capstone.demo.model.dto.request.UserAnswerRequestDto;
import com.capstone.demo.model.dto.response.UserAnswerResponseDto;
import com.capstone.demo.repository.UserAnswerRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAnswerServiceTest {

    @Mock
    private UserAnswerRepository userAnswerRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserAnswerService underTest;

    @DisplayName("It should create UserAnswer entity with data given from User and save it and then return in dto type")
    @Test
    void canCreateUserAnswer() {

        // given
        String email = "email";
        User user = new User(
                10L,
                "email",
                "username",
                "password",
                new ArrayList<>() {
                    {
                        add(new UserAnswer());
                        add(new UserAnswer());
                    }
                },
                Role.USER
        );
        given(userService.findByEmail(email))
                .willReturn(user);

        UserAnswerRequestDto requestDto = new UserAnswerRequestDto("answer");
        String topic = "topic";
        Integer questionId = 3;

        // when
        UserAnswerResponseDto actualResponseDto =
                underTest.createUserAnswer(requestDto, topic, questionId, email);

        // then
        assertUserAnswerCreatedSuccessfully(user, requestDto, topic, questionId, actualResponseDto);
    }

    private void assertUserAnswerCreatedSuccessfully(User user, UserAnswerRequestDto requestDto, String topic,
                                                     Integer questionId, UserAnswerResponseDto actualResponseDto) {
        ArgumentCaptor<UserAnswer> userAnswerArgumentCaptor =
                ArgumentCaptor.forClass(UserAnswer.class);
        verify(userAnswerRepository)
                .save(userAnswerArgumentCaptor.capture());
        UserAnswer capturedValue = userAnswerArgumentCaptor.getValue();
        UserAnswerResponseDto expectedResponseDto = UserAnswerResponseDto.of(capturedValue);

        assertThat(capturedValue)
                .extracting(UserAnswer::getAnswer, UserAnswer::getAuthor, UserAnswer::getTopic,
                        UserAnswer::getQuestionId)
                .containsExactly(requestDto.getAnswer(), user, topic, questionId);
        assertThat(actualResponseDto.getAnswer()).isEqualTo(expectedResponseDto.getAnswer());
    }

    @DisplayName("It should return UserAnswer List of given question of User in dto type")
    @Test
    void canGetUserAnswer() {

        // given
        String email = "email";
        String targetTopic = "target topic";
        Integer targetQuestionId = 3;
        User user = new User(
                10L,
                "email",
                "username",
                "password",
                new ArrayList<>() {
                    {
                        add(UserAnswer.builder()
                                .topic(targetTopic)
                                .questionId(targetQuestionId)
                                .answer("answer1")
                                .build());
                        add(UserAnswer.builder()
                                .topic("topic2")
                                .questionId(4)
                                .answer("answer2")
                                .build());
                        add(UserAnswer.builder()
                                .topic(targetTopic)
                                .questionId(targetQuestionId)
                                .answer("answer3")
                                .build());
                    }
                },
                Role.USER
        );
        given(userService.findByEmail(email))
                .willReturn(user);

        // when
        List<UserAnswerResponseDto> actualResponseList =
                underTest.getUserAnswer(targetTopic, targetQuestionId, email);

        // then
        assertThat(actualResponseList.size()).isEqualTo(2);
        assertThat(actualResponseList)
                .extracting(UserAnswerResponseDto::getAnswer)
                .containsExactlyInAnyOrder("answer1", "answer3");
    }
}