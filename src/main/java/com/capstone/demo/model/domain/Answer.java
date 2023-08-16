package com.capstone.demo.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "answers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @Column(nullable = false)
    private String content;
    @OneToMany(mappedBy = "answer")
    private List<Vote> votes;
}
