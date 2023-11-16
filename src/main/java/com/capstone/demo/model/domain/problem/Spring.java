package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "spring")
@Getter
public class Spring {

    @Id
    private int id;
    private String topic;
    private String question;
    private String answer;
}