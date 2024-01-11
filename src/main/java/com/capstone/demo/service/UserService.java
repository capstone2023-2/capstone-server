package com.capstone.demo.service;

import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.response.UserResponseDto;
import com.capstone.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

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