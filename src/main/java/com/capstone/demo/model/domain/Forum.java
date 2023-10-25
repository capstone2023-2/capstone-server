package com.capstone.demo.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "forums")
@Getter
public class Forum extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forumId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private Integer bookmarkCount;

    @Builder
    private Forum(User author, String title, String content, Integer bookmarkCount){
        this.author = author;
        this.title = title;
        this.content = content;
        this.bookmarkCount = bookmarkCount;
    }

    public void update(String updateContent){
        this.content = updateContent;
    }

    public void addBookmark() { bookmarkCount++; }

    public void removeBookmark() { bookmarkCount--; }
}