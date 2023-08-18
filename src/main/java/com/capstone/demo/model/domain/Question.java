package com.capstone.demo.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private int views;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @OneToMany(mappedBy = "question")
    private List<Comment> comments;
    @OneToMany(mappedBy = "question")
    private List<Vote> votes;
}
