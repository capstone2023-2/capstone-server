package com.capstone.demo.repository.problem;

import com.capstone.demo.model.domain.problem.OperatingSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatingSystemRepository extends JpaRepository<OperatingSystem, Integer> {
}