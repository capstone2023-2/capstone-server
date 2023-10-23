package com.capstone.demo.model;

import com.capstone.demo.model.dto.request.SocialAccountDto;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@Getter
public class SocialAccount {

    private String githubLink = "";
    private String blogLink   = "";
    private String twitterLink = "";
    private String linkedinLink = "";

    public void update(SocialAccountDto dto){
        this.githubLink = dto.getGithubLink();
        this.blogLink = dto.getBlogLink();
        this.twitterLink = dto.getTwitterLink();
        this.linkedinLink = dto.getLinkedinLink();
    }
}
