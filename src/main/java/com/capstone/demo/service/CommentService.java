package com.capstone.demo.service;

import com.capstone.demo.model.domain.Comment;
import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.CommentRequestDto;
import com.capstone.demo.model.dto.response.CommentResponseDto;
import com.capstone.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postId, String email){

        User user = userService.findByEmail(email);
        Post post = postService.findById(postId);

        Comment comment = Comment.builder()
                .author(user)
                .content(commentRequestDto.getContent())
                .post(post)
                .build();

        user.getComments().add(comment);
        post.getComments().add(comment);
        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }

    public List<CommentResponseDto> getCommentsOfPost(Long postId){

        Post findPost = postService.findById(postId);
        List<CommentResponseDto> dtoList = new ArrayList<>();

        for(Comment e: findPost.getComments()) dtoList.add(CommentResponseDto.of(e));

        return dtoList;
    }

    public CommentResponseDto updateComment(Long commentId, String updateContent){

        Comment comment = this.findById(commentId);
        comment.update(updateContent);

        return CommentResponseDto.of(comment);
    }

    public Boolean deleteComment(Long commentId){

        Comment comment = this.findById(commentId);
        Post post = comment.getPost();

        post.getComments().remove(comment);
        commentRepository.delete(comment);

        return true;
    }

    public Comment findById(Long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElseThrow(() -> new NoSuchElementException("no such comment with the id"));
    }
}
