package com.capstone.demo.service.interview;

import com.capstone.demo.model.dto.response.ProblemResponseDto;
import com.capstone.demo.service.problem.ProblemService;
import com.capstone.demo.utils.RandomNumberPicker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private static final int TOPIC_COUNT = 8;

    private final ProblemService problemService;

    /* 랜덤한 질문 한 개 선택 */
    public ProblemResponseDto getRandomInterviewProblem() {
        int randomTopic = RandomNumberPicker.generate(TOPIC_COUNT - 1);

        switch (randomTopic) {
            case 0:
                return problemService.getRandomAlgorithmProblem();
//            case 1:
//                return problemService.getRandomDatabaseProblem();
            case 2:
                return problemService.getRandomDataStructureProblem();
            case 3:
                return problemService.getRandomJavaProblem();
            case 4:
                return problemService.getRandomJavascriptProblem();
            case 5:
                return problemService.getRandomNetworkProblem();
//            case 6:
//                return problemService.getRandomOperatingSystemProblem();
            default:
                return problemService.getRandomSpringProblem();
        }
    }
}