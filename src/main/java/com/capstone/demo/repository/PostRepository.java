package com.capstone.demo.repository;

import com.capstone.demo.model.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
