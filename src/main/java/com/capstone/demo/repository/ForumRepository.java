package com.capstone.demo.repository;

import com.capstone.demo.model.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
