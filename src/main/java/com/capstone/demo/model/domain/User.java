package com.capstone.demo.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
    @OneToMany(mappedBy = "voter")
    private List<Vote> votes;
    @OneToMany(mappedBy = "author")
    private List<Thread> threads;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Collection> collections;
}
