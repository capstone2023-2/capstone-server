package com.capstone.demo.service;

import com.capstone.demo.model.domain.Answer;
import com.capstone.demo.model.domain.Question;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.AnswerRequestDto;
import com.capstone.demo.model.dto.response.AnswerResponseDto;
import com.capstone.demo.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final QuestionService questionService;

    public AnswerResponseDto createAnswer(AnswerRequestDto answerRequestDto){

        User findUser = userService.findById(answerRequestDto.getUserId());
        Question findQuestion = questionService.findById(answerRequestDto.getQuestionId());

        Answer answer = Answer.builder()
                .author(findUser)
                .content(answerRequestDto.getContent())
                .question(findQuestion)
                .comments(new ArrayList<>())
                .build();

        findUser.getAnswers().add(answer);
        findQuestion.getAnswers().add(answer);
        answerRepository.save(answer);

        return AnswerResponseDto.of(answer);
    }

    public List<AnswerResponseDto> getAnswers(){

        List<Answer> entityList = answerRepository.findAll();
        List<AnswerResponseDto> dtoList = new ArrayList<>();

        for(Answer e: entityList) dtoList.add(AnswerResponseDto.of(e));

        return dtoList;
    }

    public Answer findById(Long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        return optionalAnswer.orElseThrow(() -> new NoSuchElementException("no such answer with the id"));
    }
}
