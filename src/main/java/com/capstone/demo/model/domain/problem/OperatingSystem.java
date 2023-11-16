package com.capstone.demo.model.domain.problem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "operating_system")
@Getter
public class OperatingSystem extends Problem {

    @Id
    private int id;
}