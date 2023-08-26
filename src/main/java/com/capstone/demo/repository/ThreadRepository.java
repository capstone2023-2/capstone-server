package com.capstone.demo.repository;

import com.capstone.demo.model.domain.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
