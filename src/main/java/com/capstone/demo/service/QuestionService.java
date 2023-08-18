package com.capstone.demo.service;

import com.capstone.demo.model.domain.Question;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.QuestionRequestDto;
import com.capstone.demo.model.dto.response.QuestionResponseDto;
import com.capstone.demo.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;

    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto) {

        User findUser = userService.findById(questionRequestDto.getUserId());

        Question question = Question.builder()
                .author(findUser)
                .title(questionRequestDto.getTitle())
                .content(questionRequestDto.getContent())
                .views(0)
                .answers(new ArrayList<>())
                .comments(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        findUser.getQuestions().add(question);
        questionRepository.save(question);

        return QuestionResponseDto.of(question);
    }

    public List<QuestionResponseDto> getQuestions(){

        List<Question> entityList = questionRepository.findAll();
        List<QuestionResponseDto> dtoList = new ArrayList<>();

        for(Question e: entityList) dtoList.add(QuestionResponseDto.of(e));

        return dtoList;
    }

    public Question findById(Long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElseThrow(() -> new NoSuchElementException("no such question with the id"));
    }
}
