package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "network")
@Getter
public class Network {

    @Id
    private int id;
    private String topic;
    private String question;
    private String answer;
}