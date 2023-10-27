package com.capstone.demo.model.domain;

import com.capstone.demo.model.SocialAccount;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    @OneToMany(mappedBy = "author")
    private List<Forum> forums;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
    @OneToMany(mappedBy = "voter")
    private List<Vote> votes;
    @OneToMany(mappedBy = "author")
    private List<Thread> threads;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Collection> collections;
    @Column
    private SocialAccount socialAccount;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(String username, String email, String password, List<Post> posts, List<Forum> forums, List<Comment> comments,
                 List<Vote> votes, List<Thread> threads, List<Collection> collections, SocialAccount socialAccount) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.posts = posts;
        this.forums = forums;
        this.comments = comments;
        this.votes = votes;
        this.threads = threads;
        this.collections = collections;
        this.socialAccount = socialAccount;
        this.role = Role.USER;
    }

    public static User of(String username, String email) {

        return User.builder()
                .username(username)
                .email(email)
                .build();
    }


    public String getRoleValue() {

        return this.getRole().getValue();
    }
}