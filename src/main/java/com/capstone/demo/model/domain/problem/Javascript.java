package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "javascript")
@Getter
public class Javascript {

    @Id
    private int id;
    private String topic;
    private String question;
    private String answer;
}