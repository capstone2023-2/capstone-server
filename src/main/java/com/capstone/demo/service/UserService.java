package com.capstone.demo.service;

import com.capstone.demo.exception.AppException;
import com.capstone.demo.exception.ErrorCode;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.LoginRequest;
import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.repository.UserRepository;
import com.capstone.demo.utils.JwtUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;


    public void join(UserRegisterDto userRegisterDto) {

        userRepository.findByEmail(userRegisterDto.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.EMAIL_DUPLICATED, "이미 존재하는 email입니다.");
                });

        userRepository.findByUsername(userRegisterDto.getUsername())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, "이미 존재하는 username입니다.");
                });

        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .username(userRegisterDto.getUsername())
                .password(encoder.encode(userRegisterDto.getPassword()))
                .userAnswers(new ArrayList<>())
                .build();
        userRepository.save(user);

    }

    public String login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new AppException(ErrorCode.EMAIL_NOT_FOUND, loginRequest.getEmail() + "이 존재하지 않습니다."));

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 password를 입력하셨습니다.");
        }

        return JwtUtil.createAccessToken(user.getEmail());
    }

    public UserResponseDto getUser(Long id) {

        User user = this.findById(id);
        return UserResponseDto.of(user);
    }

    public List<UserResponseDto> getUsers() {

        List<User> entityList = userRepository.findAll();
        List<UserResponseDto> dtoList = new ArrayList<>();

        for (User e : entityList) {
            dtoList.add(UserResponseDto.of(e));
        }

        return dtoList;
    }

    public User findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new NoSuchElementException("no such user with the id"));
    }

    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(() -> new NoSuchElementException("no such user with the email"));
    }
}