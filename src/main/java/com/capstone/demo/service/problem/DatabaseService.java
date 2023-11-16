package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.Database;
import com.capstone.demo.repository.problem.DatabaseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final DatabaseRepository databaseRepository;

    public List<Database> getProblems(){
        return databaseRepository.findAll();
    }
}