package com.capstone.demo.repository;

import com.capstone.demo.model.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByUserId(Long userId);
    Integer deleteByUserIdAndPostId(Long userId, Long postId);
    Integer deleteAllByPostId(Long postId);
}
