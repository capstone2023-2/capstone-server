package com.capstone.demo.model.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<UserAnswer> userAnswers;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(String username, String email, String password, List<UserAnswer> userAnswers) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userAnswers = userAnswers;
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