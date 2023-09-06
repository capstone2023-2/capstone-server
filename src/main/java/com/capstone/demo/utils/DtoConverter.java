package com.capstone.demo.utils;

import com.capstone.demo.model.domain.Collection;
import com.capstone.demo.model.domain.Comment;
import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.dto.response.CollectionResponseDto;
import com.capstone.demo.model.dto.response.CommentResponseDto;
import com.capstone.demo.model.dto.response.PostResponseDto;
import com.capstone.demo.model.dto.response.ThreadResponseDto;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

    public static List<PostResponseDto> convertPostsToResponseDto(List<Post> posts) {

        List<PostResponseDto> result = new ArrayList<>();
        for(Post e: posts) result.add(PostResponseDto.of(e));

        return result;
    }

    public static List<CommentResponseDto> convertCommentsToResponseDto(List<Comment> comments){

        List<CommentResponseDto> result = new ArrayList<>();
        for(Comment e: comments){
            result.add(CommentResponseDto.of(e));
        }

        return result;
    }

    public static List<ThreadResponseDto> convertThreadsToResponseDto(List<Thread> threads) {

        List<ThreadResponseDto> result = new ArrayList<>();
        for(Thread e: threads){
            result.add(ThreadResponseDto.of(e));
        }

        return result;
    }

    public static List<CollectionResponseDto> convertCollectionsToResponseDto(List<Collection> collections){

        List<CollectionResponseDto> result = new ArrayList<>();
        for(Collection e: collections){
            result.add(CollectionResponseDto.of(e));
        }

        return result;
    }
}
