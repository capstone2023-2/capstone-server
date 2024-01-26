package com.capstone.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.capstone.demo.model.domain.Role;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.domain.UserAnswer;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService underTest;

    @DisplayName("It should return UserResponseDto which has consistent data with User found by id")
    @Test
    void canGetUser() {

        // given
        long id = 10;
        User expectedUser = new User(
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
        given(userRepository.findById(id))
                .willReturn(Optional.of(expectedUser));

        // when
        UserResponseDto actualResponseDto = underTest.getUser(id);

        // then
        assertThat(actualResponseDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("userId", expectedUser.getUserId())
                .hasFieldOrPropertyWithValue("email", expectedUser.getEmail())
                .hasFieldOrPropertyWithValue("username", expectedUser.getUsername());
        assertThat(actualResponseDto.getUserAnswers().size())
                .isEqualTo(expectedUser.getUserAnswers().size());
    }

    @DisplayName("It should convert every User entities to UserResponseDto and return the dto list")
    @Test
    void canGetUsers() {

        // given
        List<User> entityList = List.of(
                new User(
                        1L,
                        "email1",
                        "username1",
                        "password1",
                        List.of(new UserAnswer()),
                        Role.USER
                ),
                new User(
                        2L,
                        "email2",
                        "username2",
                        "password2",
                        List.of(new UserAnswer()),
                        Role.USER
                )
        );
        given(userRepository.findAll())
                .willReturn(entityList);

        // when
        List<UserResponseDto> actualDtoList = underTest.getUsers();

        // then
        verify(userRepository).findAll();
        assertThat(actualDtoList.size()).isEqualTo(entityList.size());
    }

    @DisplayName("It should be able to find User with given id")
    @Test
    void canFindById() {

        // given
        long id = 10;
        User expectedUser = new User(
                10L,
                "email",
                "username",
                "password",
                new ArrayList<>(),
                Role.USER
        );
        given(userRepository.findById(id))
                .willReturn(Optional.of(expectedUser));

        // when
        User actualUser = underTest.findById(id);

        // then
        verify(userRepository).findById(id);
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @DisplayName("It should throw exception on finding User with given id")
    @Test
    void willThrowWhenIdNotFound() {

        // given
        long id = 10;
        given(userRepository.findById(id))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("no such user with the id");
    }

    @DisplayName("It should be able to find User with given email")
    @Test
    void canFindByEmail() {

        // given
        String email = "email";
        User expectedUser = new User(
                10L,
                "email",
                "username",
                "password",
                new ArrayList<>(),
                Role.USER
        );
        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(expectedUser));

        // when
        User actualUser = underTest.findByEmail(email);

        // then
        verify(userRepository).findByEmail(email);
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @DisplayName("It should throw exception on finding User with given email")
    @Test
    void willThrowWhenEmailNotFound() {

        // given
        String email = "email";
        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> underTest.findByEmail(email))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("no such user with the email");
    }
}