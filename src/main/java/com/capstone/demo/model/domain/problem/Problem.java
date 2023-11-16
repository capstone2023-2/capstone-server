package com.capstone.demo.model.domain.problem;

import javax.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class Problem {

    private String topic;
    private String question;
    private String answer;
}