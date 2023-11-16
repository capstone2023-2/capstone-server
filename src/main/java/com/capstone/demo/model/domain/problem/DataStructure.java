package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "data_structure")
@Getter
public class DataStructure extends Problem {

    @Id
    private int id;
}