package com.capstone.demo.repository.problem;

import com.capstone.demo.model.domain.problem.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Integer> {
}