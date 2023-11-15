package com.capstone.demo.repository;

import com.capstone.demo.model.domain.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
}