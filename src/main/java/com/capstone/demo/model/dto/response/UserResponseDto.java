package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.SocialAccount;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.utils.DtoConverter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    private String email;
    private String username;
    private Long userId;
    private List<PostResponseDto> posts;
    private List<CommentResponseDto> comments;
    private List<CollectionResponseDto> collections;
    private SocialAccount socialAccount;
    private LocalDateTime createdAt;

    public static UserResponseDto of(User user){
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .posts(DtoConverter.convertPostsToResponseDto(user.getPosts()))
                .comments(DtoConverter.convertCommentsToResponseDto(user.getComments()))
                .collections(DtoConverter.convertCollectionsToResponseDto(user.getCollections()))
                .socialAccount(user.getSocialAccount())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
