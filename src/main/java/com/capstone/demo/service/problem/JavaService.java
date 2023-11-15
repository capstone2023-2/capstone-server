package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.Java;
import com.capstone.demo.repository.problem.JavaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JavaService {

    private final JavaRepository javaRepository;

    public List<Java> getProblems(){
        return javaRepository.findAll();
    }
}