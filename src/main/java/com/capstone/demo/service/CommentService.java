package com.capstone.demo.service;

import com.capstone.demo.model.domain.Answer;
import com.capstone.demo.model.domain.Comment;
import com.capstone.demo.model.domain.Question;
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
    private final QuestionService questionService;
    private final AnswerService answerService;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto){

        User findUser = userService.findById(commentRequestDto.getUserId());
        Question findQuestion = questionService.findById(commentRequestDto.getQuestionId());
        Answer findAnswer = commentRequestDto.getAnswerId() != null ?
                answerService.findById(commentRequestDto.getAnswerId()) : null;


        Comment comment = Comment.builder()
                .author(findUser)
                .question(findQuestion)
                .content(commentRequestDto.getContent())
                .answer(commentRequestDto.getAnswerId() != null ?
                        findAnswer : null)
                .build();

        findUser.getComments().add(comment);
        findQuestion.getComments().add(comment);
        if(findAnswer!=null)    findAnswer.getComments().add(comment);
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
