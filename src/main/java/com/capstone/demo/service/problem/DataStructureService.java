package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.DataStructure;
import com.capstone.demo.repository.problem.DataStructureRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataStructureService {

    private final DataStructureRepository dataStructureRepository;

    public List<DataStructure> getProblems(){
        return dataStructureRepository.findAll();
    }
}