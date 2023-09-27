package com.capstone.demo.service;

import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.UserRegisterDto;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto registerUser(UserRegisterDto userRegisterDto) {

        return UserResponseDto.of(userRepository.save(User.builder()
                .email(userRegisterDto.getEmail())
                .username(userRegisterDto.getUsername())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .collections(new ArrayList<>())
                .build()));
    }

    public List<UserResponseDto> getUsers() {

        List<User> entityList = userRepository.findAll();
        List<UserResponseDto> dtoList = new ArrayList<>();

        for (User e : entityList) dtoList.add(UserResponseDto.of(e));

        return dtoList;
    }

    public User findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new NoSuchElementException("no such user with the id"));
    }
}
