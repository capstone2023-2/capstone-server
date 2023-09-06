package com.capstone.demo.service;

import com.capstone.demo.model.domain.Comment;
import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.CommentRequestDto;
import com.capstone.demo.model.dto.response.CommentResponseDto;
import com.capstone.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto){

        User findUser = userService.findById(commentRequestDto.getUserId());
        Post findPost = postService.findById(commentRequestDto.getPostId());

        Comment comment = Comment.builder()
                .author(findUser)
                .content(commentRequestDto.getContent())
                .post(findPost)
                .build();

        findUser.getComments().add(comment);
        findPost.getComments().add(comment);
        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }

    public List<CommentResponseDto> getComments(){

        List<Comment> entityList = commentRepository.findAll();
        List<CommentResponseDto> dtoList = new ArrayList<>();

        for(Comment e: entityList) dtoList.add(CommentResponseDto.of(e));

        return dtoList;
    }

    public Boolean deleteComment(Long commentId){

        Comment findComment = this.findById(commentId);
        Post findPost = postService.findById(findComment.getPost().getPostId());

        findPost.getComments().remove(findComment);
        commentRepository.delete(findComment);

        return true;
    }

    public Comment findById(Long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElseThrow(() -> new NoSuchElementException("no such comment with the id"));
    }
}
