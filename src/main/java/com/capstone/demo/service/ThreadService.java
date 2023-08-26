package com.capstone.demo.service;

import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.ThreadRequestDto;
import com.capstone.demo.model.dto.response.ThreadResponseDto;
import com.capstone.demo.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final UserService userService;

    public ThreadResponseDto createThread(ThreadRequestDto threadRequestDto){

        User findUser = userService.findById(threadRequestDto.getUserId());

        Thread thread = Thread.builder()
                .author(findUser)
                .name(threadRequestDto.getName())
                .description(threadRequestDto.getDescription())
                .posts(new ArrayList<>())
                .build();

        findUser.getThreads().add(thread);
        threadRepository.save(thread);

        return ThreadResponseDto.of(thread);
    }

    public List<ThreadResponseDto> getThreads(){

        List<Thread> entityList = threadRepository.findAll();
        List<ThreadResponseDto> dtoList = new ArrayList<>();

        for(Thread e: entityList) dtoList.add(ThreadResponseDto.of(e));

        return dtoList;
    }

    public Thread findById(Long threadId){
        Optional<Thread> optionalThread = threadRepository.findById(threadId);
        return optionalThread.orElseThrow(() -> new NoSuchElementException("no such thread with the id"));
    }
}
