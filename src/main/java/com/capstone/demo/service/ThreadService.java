package com.capstone.demo.service;

import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.ThreadRequestDto;
import com.capstone.demo.model.dto.response.ThreadResponseDto;
import com.capstone.demo.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final UserService userService;

    public ThreadResponseDto addThread(ThreadRequestDto threadRequestDto, String email){

        User user = userService.findByEmail(email);

        Thread thread = Thread.builder()
                .author(user)
                .name(threadRequestDto.getName())
                .description(threadRequestDto.getDescription())
                .posts(new ArrayList<>())
                .build();

        user.getThreads().add(thread);
        threadRepository.save(thread);

        return ThreadResponseDto.of(thread);
    }

    public ThreadResponseDto getThread(Long id){

        Thread thread = this.findById(id);
        return ThreadResponseDto.of(thread);
    }

    public ThreadResponseDto getThread(Long collectionId, Long threadId){

        Thread thread = this.findById(threadId);
        ThreadResponseDto threadResponseDto = ThreadResponseDto.of(thread);
        threadResponseDto.setCollectionId(collectionId);
        return threadResponseDto;
    }

    public List<ThreadResponseDto> getThreads(){

        List<Thread> entityList = threadRepository.findAll();
        List<ThreadResponseDto> dtoList = new ArrayList<>();

        for(Thread e: entityList) dtoList.add(ThreadResponseDto.of(e));

        return dtoList;
    }

    public Boolean deleteThread(Long threadId, String email){

        Thread thread = this.findById(threadId);
        User user = userService.findByEmail(email);

        user.getThreads().remove(thread);
        threadRepository.delete(thread);

        return true;
    }

    public Thread findById(Long threadId){
        Optional<Thread> optionalThread = threadRepository.findById(threadId);
        return optionalThread.orElseThrow(() -> new NoSuchElementException("no such thread with the id"));
    }
}
