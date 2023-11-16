package com.capstone.demo.service.problem;


import com.capstone.demo.model.domain.problem.Algorithm;
import com.capstone.demo.repository.problem.AlgorithmRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;

    public List<Algorithm> getProblems(){
        return algorithmRepository.findAll();
    }
}