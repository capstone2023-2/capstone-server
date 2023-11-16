package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.Spring;
import com.capstone.demo.repository.problem.SpringRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringService {

    private final SpringRepository springRepository;

    public List<Spring> getProblems(){
        return springRepository.findAll();
    }
}