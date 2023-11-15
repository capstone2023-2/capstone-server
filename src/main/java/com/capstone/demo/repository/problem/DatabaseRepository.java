package com.capstone.demo.repository.problem;

import com.capstone.demo.model.domain.problem.Database;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseRepository extends JpaRepository<Database, Integer> {
}