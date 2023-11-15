package com.capstone.demo.repository.problem;

import com.capstone.demo.model.domain.problem.DataStructure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataStructureRepository extends JpaRepository<DataStructure, Integer> {
}