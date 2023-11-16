package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.Algorithm;
import com.capstone.demo.model.domain.problem.DataStructure;
import com.capstone.demo.model.domain.problem.Database;
import com.capstone.demo.model.domain.problem.Java;
import com.capstone.demo.model.domain.problem.Javascript;
import com.capstone.demo.model.domain.problem.Network;
import com.capstone.demo.model.domain.problem.OperatingSystem;
import com.capstone.demo.model.domain.problem.Spring;
import com.capstone.demo.repository.problem.AlgorithmRepository;
import com.capstone.demo.repository.problem.DataStructureRepository;
import com.capstone.demo.repository.problem.DatabaseRepository;
import com.capstone.demo.repository.problem.JavaRepository;
import com.capstone.demo.repository.problem.JavascriptRepository;
import com.capstone.demo.repository.problem.NetworkRepository;
import com.capstone.demo.repository.problem.OperatingSystemRepository;
import com.capstone.demo.repository.problem.SpringRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final AlgorithmRepository algorithmRepository;
    private final DatabaseRepository databaseRepository;
    private final DataStructureRepository dataStructureRepository;
    private final JavascriptRepository javascriptRepository;
    private final JavaRepository javaRepository;
    private final NetworkRepository networkRepository;
    private final OperatingSystemRepository operatingSystemRepository;
    private final SpringRepository springRepository;

    public List<Algorithm> getAlgorithmProblems() {
        return algorithmRepository.findAll();
    }

    public List<Database> getDatabaseProblems() {
        return databaseRepository.findAll();
    }

    public List<DataStructure> getDataStructureProblems() {
        return dataStructureRepository.findAll();
    }

    public List<Javascript> getJavascriptProblems() {
        return javascriptRepository.findAll();
    }

    public List<Java> getJavaProblems() {
        return javaRepository.findAll();
    }

    public List<Network> getNetworkProblems() {
        return networkRepository.findAll();
    }

    public List<OperatingSystem> getOperatingSystemProblems() {
        return operatingSystemRepository.findAll();
    }

    public List<Spring> getSpringProblems() {
        return springRepository.findAll();
    }
}