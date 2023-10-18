package com.capstone.demo.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bookmarks")
@Getter
@NoArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long postId;

    @Builder
    private Bookmark(Long userId, Long postId){
        this.userId = userId;
        this.postId = postId;
    }
}