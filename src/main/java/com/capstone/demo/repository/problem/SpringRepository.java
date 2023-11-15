package com.capstone.demo.repository.problem;

import com.capstone.demo.model.domain.problem.Spring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringRepository extends JpaRepository<Spring, Integer> {
}