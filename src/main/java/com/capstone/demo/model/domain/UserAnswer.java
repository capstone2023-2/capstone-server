package com.capstone.demo.model.domain;

import javax.persistence.Entity;
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
@Table(name = "user_answer")
@Getter
@NoArgsConstructor
public class UserAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private String topic;
    private Integer questionId;
    private String answer;

    @Builder
    private UserAnswer(User author, String topic, Integer questionId, String answer) {
        this.author = author;
        this.topic = topic;
        this.questionId = questionId;
        this.answer = answer;
    }
}