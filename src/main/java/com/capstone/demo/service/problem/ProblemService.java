package com.capstone.demo.service.problem;

import com.capstone.demo.model.domain.problem.Algorithm;
import com.capstone.demo.model.domain.problem.DataStructure;
import com.capstone.demo.model.domain.problem.Database;
import com.capstone.demo.model.domain.problem.Java;
import com.capstone.demo.model.domain.problem.Javascript;
import com.capstone.demo.model.domain.problem.Network;
import com.capstone.demo.model.domain.problem.OperatingSystem;
import com.capstone.demo.model.domain.problem.Spring;
import com.capstone.demo.model.dto.response.ProblemResponseDto;
import com.capstone.demo.repository.problem.AlgorithmRepository;
import com.capstone.demo.repository.problem.DataStructureRepository;
import com.capstone.demo.repository.problem.DatabaseRepository;
import com.capstone.demo.repository.problem.JavaRepository;
import com.capstone.demo.repository.problem.JavascriptRepository;
import com.capstone.demo.repository.problem.NetworkRepository;
import com.capstone.demo.repository.problem.OperatingSystemRepository;
import com.capstone.demo.repository.problem.SpringRepository;
import com.capstone.demo.utils.RandomNumberPicker;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<ProblemResponseDto> getAlgorithmProblems() {
        List<Algorithm> list = algorithmRepository.findAll();
        return list.stream()
                .map(Algorithm::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomAlgorithmProblem() {
        List<ProblemResponseDto> problems = getAlgorithmProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }

    public List<ProblemResponseDto> getDatabaseProblems() {
        List<Database> list = databaseRepository.findAll();
        return list.stream()
                .map(Database::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomDatabaseProblem() {
        List<ProblemResponseDto> problems = getDatabaseProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }

    public List<ProblemResponseDto> getDataStructureProblems() {
        List<DataStructure> list = dataStructureRepository.findAll();
        return list.stream()
                .map(DataStructure::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomDataStructureProblem() {
        List<ProblemResponseDto> problems = getDataStructureProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }

    public List<ProblemResponseDto> getJavascriptProblems() {
        List<Javascript> list = javascriptRepository.findAll();
        return list.stream()
                .map(Javascript::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomJavascriptProblem() {
        List<ProblemResponseDto> problems = getJavascriptProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }

    public List<ProblemResponseDto> getJavaProblems() {
        List<Java> list = javaRepository.findAll();
        return list.stream()
                .map(Java::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomJavaProblem() {
        List<ProblemResponseDto> problems = getJavaProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }

    public List<ProblemResponseDto> getNetworkProblems() {
        List<Network> list = networkRepository.findAll();
        return list.stream()
                .map(Network::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomNetworkProblem() {
        List<ProblemResponseDto> problems = getNetworkProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }

    public List<ProblemResponseDto> getOperatingSystemProblems() {
        List<OperatingSystem> list = operatingSystemRepository.findAll();
        return list.stream()
                .map(OperatingSystem::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomOperatingSystemProblem() {
        List<ProblemResponseDto> problems = getOperatingSystemProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }

    public List<ProblemResponseDto> getSpringProblems() {
        List<Spring> list = springRepository.findAll();
        return list.stream()
                .map(Spring::of)
                .collect(Collectors.toList());
    }

    public ProblemResponseDto getRandomSpringProblem() {
        List<ProblemResponseDto> problems = getSpringProblems();
        int size = problems.size();
        int random = RandomNumberPicker.generate(size - 1);

        return problems.get(random);
    }
}