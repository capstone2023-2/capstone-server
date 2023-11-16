package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "database_problem")
@Getter
public class Database {

    @Id
    private int id;
    private String topic;
    private String question;
    private String answer;
}