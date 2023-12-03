package com.capstone.demo.model.domain.problem;

import com.capstone.demo.model.dto.response.ProblemResponseDto;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "database_problem")
@Getter
public class Database extends Problem {

    @Id
    private int id;

    public static ProblemResponseDto of(Database o) {
        return ProblemResponseDto.builder()
                .id(o.getId())
                .topic(o.getTopic())
                .question(o.getQuestion())
                .answer(o.getAnswer())
                .audio(o.getAudio())
                .build();
    }
}