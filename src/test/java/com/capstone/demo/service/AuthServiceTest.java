package com.capstone.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.capstone.demo.exception.AppException;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder encoder;
    @InjectMocks
    private AuthService underTest;

    private UserRegisterDto registerDto;
    private UserRegisterDto registerDtoWithDuplicatedEmail;
    private UserRegisterDto registerDtoWithDuplicateUsername;
    private UserRegisterDto registerDtoWithPasswordCheckFails;
    private User userWithDuplicateData;

    @BeforeEach
    void setup() {
        registerDto = new UserRegisterDto(
                "email",
                "password",
                "password",
                "username"
        );

        registerDtoWithDuplicatedEmail = new UserRegisterDto(
                "duplicated email",
                "password",
                "password",
                "username"
        );

        registerDtoWithDuplicateUsername = new UserRegisterDto(
                "email",
                "password",
                "password",
                "duplicated username"
        );

        registerDtoWithPasswordCheckFails = new UserRegisterDto(
                "email",
                "password",
                "password check fails",
                "username"
        );

        userWithDuplicateData = new User();
    }

    @DisplayName("Fresh email with fresh username should be able to join successfully")
    @Test
    void canJoin() {

        // given
        given(userRepository.findByEmail(registerDto.getEmail()))
                .willReturn(Optional.empty());
        given(userRepository.findByUsername(registerDto.getUsername()))
                .willReturn(Optional.empty());
        given(encoder.encode(registerDto.getPassword()))
                .willReturn("encoded password");

        // when
        UserResponseDto actualResponseDto = underTest.join(registerDto);

        // then
        verify(userRepository).findByEmail(anyString());
        verify(userRepository).findByUsername(anyString());

        assertUserJoinedSuccessfullyWithFreshEmailAndFreshUsername(registerDto, actualResponseDto);
    }

    private void assertUserJoinedSuccessfullyWithFreshEmailAndFreshUsername(UserRegisterDto registerDto,
                                                                            UserResponseDto actualResponseDto) {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedValue = userArgumentCaptor.getValue();
        UserResponseDto expectedResponseDto = UserResponseDto.of(capturedValue);

        assertThat(capturedValue)
                .extracting(User::getEmail, User::getUsername, User::getPassword)
                .containsExactly(registerDto.getEmail(), registerDto.getUsername(), "encoded password");

        assertThat(actualResponseDto)
                .extracting(UserResponseDto::getEmail, UserResponseDto::getUsername)
                .containsExactly(expectedResponseDto.getEmail(), expectedResponseDto.getUsername());
    }

    @DisplayName("Duplicated email with fresh username should not be able to join")
    @Test
    void cannotJoinWithDuplicatedEmail() {

        // given
        given(userRepository.findByEmail(registerDtoWithDuplicatedEmail.getEmail()))
                .willReturn(Optional.of(userWithDuplicateData));

        // when, then
        assertThatThrownBy(() -> underTest.join(registerDtoWithDuplicatedEmail))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("이미 존재하는 email입니다.");
        verify(userRepository).findByEmail(registerDtoWithDuplicatedEmail.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Fresh email with duplicated username should not be able to join")
    @Test
    void cannotJoinWithDuplicatedUsername() {

        // given
        given(userRepository.findByUsername(registerDtoWithDuplicateUsername.getUsername()))
                .willReturn(Optional.of(userWithDuplicateData));

        // when, then
        assertThatThrownBy(() -> underTest.join(registerDtoWithDuplicateUsername))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("이미 존재하는 username입니다.");
        verify(userRepository).findByUsername(registerDtoWithDuplicateUsername.getUsername());
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Failing password check should not be able to join though it has fresh email and fresh username")
    @Test
    void cannotJoinWhenPasswordCheckFails() {

        // given
        given(userRepository.findByEmail(registerDtoWithPasswordCheckFails.getEmail()))
                .willReturn(Optional.empty());
        given(userRepository.findByUsername(registerDtoWithPasswordCheckFails.getUsername()))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> underTest.join(registerDtoWithPasswordCheckFails))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
        verify(userRepository, never()).save(any(User.class));
    }
}