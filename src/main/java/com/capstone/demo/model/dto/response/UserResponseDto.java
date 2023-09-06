package com.capstone.demo.model.dto.response;

import com.capstone.demo.model.domain.User;
import com.capstone.demo.utils.DtoConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserResponseDto {

    private String email;
    private String username;
    private List<PostResponseDto> posts;
    private List<CommentResponseDto> comments;
    private List<CollectionResponseDto> collections;

    public static UserResponseDto of(User user){
        return UserResponseDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .posts(DtoConverter.convertPostsToResponseDto(user.getPosts()))
                .comments(DtoConverter.convertCommentsToResponseDto(user.getComments()))
                .collections(DtoConverter.convertCollectionsToResponseDto(user.getCollections()))
                .build();
    }

}
