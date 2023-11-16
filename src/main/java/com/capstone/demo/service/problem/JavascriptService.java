package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.Javascript;
import com.capstone.demo.repository.problem.JavascriptRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JavascriptService {

    private final JavascriptRepository javascriptRepository;

    public List<Javascript> getProblems(){
        return javascriptRepository.findAll();
    }
}