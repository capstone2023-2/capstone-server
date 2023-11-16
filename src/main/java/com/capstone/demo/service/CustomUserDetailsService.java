package com.capstone.demo.service;

import com.capstone.demo.jwt.CustomUserDetails;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " 사용자를 찾을 수 없습니다."));

        if(user!=null){
            return new CustomUserDetails(user);
        }

        return null;
    }
}