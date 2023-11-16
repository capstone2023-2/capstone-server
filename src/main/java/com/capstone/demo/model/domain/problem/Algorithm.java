package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "algorithm")
@Getter
public class Algorithm extends Problem {

    @Id
    private Integer id;
}