package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.OperatingSystem;
import com.capstone.demo.repository.problem.OperatingSystemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperatingSystemService {

    private final OperatingSystemRepository operatingSystemRepository;

    public List<OperatingSystem> getProblems(){
        return operatingSystemRepository.findAll();
    }
}