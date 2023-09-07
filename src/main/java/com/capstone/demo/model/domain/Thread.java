package com.capstone.demo.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "threads")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Thread extends BaseTimeEntity {

    @Id
    @Column(name = "thread_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long threadId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany(mappedBy = "thread", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Post> posts;
    @ManyToMany(mappedBy = "threads")
    private List<Collection> collections;
}
