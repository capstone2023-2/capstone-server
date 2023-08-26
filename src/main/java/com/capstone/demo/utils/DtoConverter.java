package com.capstone.demo.utils;

import com.capstone.demo.model.domain.Answer;
import com.capstone.demo.model.domain.Comment;
import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.dto.response.AnswerResponseDto;
import com.capstone.demo.model.dto.response.CommentResponseDto;
import com.capstone.demo.model.dto.response.PostResponseDto;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

    public static List<PostResponseDto> convertPostsToResponseDto(List<Post> posts) {

        List<PostResponseDto> result = new ArrayList<>();
        for(Post e: posts) result.add(PostResponseDto.of(e));

        return result;
    }

    public static List<AnswerResponseDto> convertAnswersToResponseDto(List<Answer> answers) {

        List<AnswerResponseDto> result = new ArrayList<>();
        for(Answer e: answers) result.add(AnswerResponseDto.of(e));

        return result;
    }

    public static List<CommentResponseDto> convertCommentsToResponseDto(List<Comment> comments){

        List<CommentResponseDto> result = new ArrayList<>();
        for(Comment e: comments){
            result.add(CommentResponseDto.of(e));
        }

        return result;
    }
}
