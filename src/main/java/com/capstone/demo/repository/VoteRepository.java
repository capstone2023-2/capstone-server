package com.capstone.demo.repository;

import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostAndVoter(Post post, User voter); // 이미 해당 사용자가 해당 Post에 vote 한 이력이 있는지
}
