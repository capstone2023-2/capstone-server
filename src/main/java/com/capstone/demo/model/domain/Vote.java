package com.capstone.demo.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    @ManyToOne
    @JoinColumn(name = "voter_id")
    private User voter;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(nullable = false)
    @Range(min = -1, max = 1)
    private int upOrDown;
}