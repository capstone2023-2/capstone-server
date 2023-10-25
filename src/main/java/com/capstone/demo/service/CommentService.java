package com.capstone.demo.service;

import com.capstone.demo.exception.AppException;
import com.capstone.demo.exception.ErrorCode;
import com.capstone.demo.model.domain.Comment;
import com.capstone.demo.model.domain.Forum;
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
    private final ForumService forumService;

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

    public CommentResponseDto createCommentOfForum(CommentRequestDto commentRequestDto, Long forumId, String email){

        User user = userService.findByEmail(email);
        Forum forum = forumService.findById(forumId);

        Comment comment = Comment.builder()
                .author(user)
                .content(commentRequestDto.getContent())
                .forum(forum)
                .build();

        user.getComments().add(comment);
        forum.getComments().add(comment);
        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }

    public List<CommentResponseDto> getCommentsOfPost(Long postId){

        Post findPost = postService.findById(postId);
        List<CommentResponseDto> dtoList = new ArrayList<>();

        for(Comment e: findPost.getComments()) dtoList.add(CommentResponseDto.of(e));

        return dtoList;
    }

    public List<CommentResponseDto> getCommentsOfForum(Long forumId){

        Forum forum = forumService.findById(forumId);
        List<Comment> commentList = forum.getComments();
        List<CommentResponseDto> dtoList = new ArrayList<>();


        for(Comment e: commentList){
            dtoList.add(CommentResponseDto.of(e));
        }

        return dtoList;
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto updateContent, String email){

        Comment comment = this.findById(commentId);
        User user = userService.findByEmail(email);

        if(!comment.getAuthor().equals(user)){
            throw new AppException(ErrorCode.UNAUTHORIZED_TRIAL, "댓글 작성자만 수정할 수 있습니다.");
        }

        comment.update(updateContent.getContent());
        commentRepository.save(comment);

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
