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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String email;
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

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column
    private String provider;
    @Column
    private String providerId;

    @Builder
    private User(String username, String email, List<Post> posts, List<Comment> comments,
                 List<Vote> votes, List<Thread> threads, List<Collection> collections,
                 String provider, String providerId) {
        this.username = username;
        this.email = email;
        this.posts = posts;
        this.comments = comments;
        this.votes = votes;
        this.threads = threads;
        this.collections = collections;
        this.role = UserRole.USER;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static User of(String username, String email, String provider, String providerId) {

        return User.builder()
                .username(username)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public void updateProvider(String provider) {

        this.provider = provider;
    }

    public User updateUsernameAndEmail(String username, String email){

        this.username = username;
        this.email = email;

        return this;
    }

    public String getRoleValue() {

        return this.getRole().getValue();
    }
}
