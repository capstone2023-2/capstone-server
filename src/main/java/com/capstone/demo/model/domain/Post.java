package com.capstone.demo.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    private Thread thread;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private int views;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Vote> votes;
}
