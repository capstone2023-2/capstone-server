package com.capstone.demo.service;

import com.capstone.demo.exception.AppException;
import com.capstone.demo.exception.ErrorCode;
import com.capstone.demo.model.domain.Forum;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.ForumRequestDto;
import com.capstone.demo.model.dto.response.ForumResponseDto;
import com.capstone.demo.repository.ForumRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final UserService userService;

    public ForumResponseDto createForum(ForumRequestDto forumRequestDto, String email){

        User user = userService.findByEmail(email);

        Forum forum = Forum.builder()
                .author(user)
                .title(forumRequestDto.getTitle())
                .content(forumRequestDto.getContent())
                .bookmarkCount(0)
                .build();

        user.getForums().add(forum);
        forumRepository.save(forum);

        return ForumResponseDto.of(forum);
    }

    public List<ForumResponseDto> getForums(){

        List<Forum> entityList = forumRepository.findAll();
        List<ForumResponseDto> dtoList = new ArrayList<>();

        for(Forum e: entityList){
            dtoList.add(ForumResponseDto.of(e));
        }

        return dtoList;
    }

    public ForumResponseDto getForum(Long forumId){

        Forum forum = findById(forumId);

        return ForumResponseDto.of(forum);
    }

    public ForumResponseDto updateForum(Long forumId, String updateContent, String email){

        Forum forum = findById(forumId);
        User user = userService.findByEmail(email);

        if(!forum.getAuthor().equals(user)){
            throw new AppException(ErrorCode.UNAUTHORIZED_TRIAL, "작성자만 수정 가능합니다.");
        }

        forum.update(updateContent);

        return ForumResponseDto.of(forum);
    }

    public Boolean deleteForum(Long forumId, String email){

        Forum forum = findById(forumId);
        User user = userService.findByEmail(email);

        if(!user.equals(forum.getAuthor())){
            throw new AppException(ErrorCode.UNAUTHORIZED_TRIAL, "작성자만 삭제 가능합니다.");
        }

        user.getForums().remove(forum);
        forumRepository.delete(forum);

        return true;
    }

    public Forum findById(Long forumId){
        Optional<Forum> optionalForum = forumRepository.findById(forumId);
        return optionalForum.orElseThrow(() -> new NoSuchElementException("no such forum with the id"));
    }
}