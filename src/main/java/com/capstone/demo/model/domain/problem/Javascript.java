package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "javascript")
@Getter
public class Javascript extends Problem {

    @Id
    private int id;
}