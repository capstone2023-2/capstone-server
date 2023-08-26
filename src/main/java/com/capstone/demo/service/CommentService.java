package com.capstone.demo.service;

import com.capstone.demo.model.domain.Answer;
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
    private final AnswerService answerService;

    public CommentResponseDto createAnswerComment(CommentRequestDto.AnswerRequest answerRequest){

        User findUser = userService.findById(answerRequest.getUserId());
        Answer findAnswer = answerService.findById(answerRequest.getAnswerId());

        Comment comment = Comment.builder()
                .author(findUser)
                .content(answerRequest.getContent())
                .answer(findAnswer)
                .build();

        findUser.getComments().add(comment);
        findAnswer.getComments().add(comment);
        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }

    public CommentResponseDto createPostComment(CommentRequestDto.QuestionRequest questionRequest){

        User findUser = userService.findById(questionRequest.getUserId());
        Post findPost = postService.findById(questionRequest.getPostId());

        Comment comment = Comment.builder()
                .author(findUser)
                .content(questionRequest.getContent())
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

    public Comment findById(Long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElseThrow(() -> new NoSuchElementException("no such comment with the id"));
    }
}
