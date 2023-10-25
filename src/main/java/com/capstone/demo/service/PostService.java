package com.capstone.demo.service;

import com.capstone.demo.exception.AppException;
import com.capstone.demo.exception.ErrorCode;
import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.PostRequestDto;
import com.capstone.demo.model.dto.response.PostResponseDto;
import com.capstone.demo.repository.BookmarkRepository;
import com.capstone.demo.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final ThreadService threadService;

    public PostResponseDto createPost(PostRequestDto postRequestDto, Long threadId ,String email) {

        User findUser = userService.findByEmail(email);
        Thread findThread = threadService.findById(threadId);

        Post post = Post.builder()
                .author(findUser)
                .thread(findThread)
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .views(0)
                .bookmarkCount(0)
                .comments(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        findUser.getPosts().add(post);
        findThread.getPosts().add(post);
        postRepository.save(post);

        return PostResponseDto.of(post);
    }

    public List<PostResponseDto> getPostsOfThread(Long threadId){

        Thread thread = threadService.findById(threadId);

        List<Post> entityList = thread.getPosts();
        List<PostResponseDto> dtoList = new ArrayList<>();

        for(Post e: entityList) dtoList.add(PostResponseDto.of(e));

        return dtoList;
    }

    public PostResponseDto getPost(Long postId){

        Post post = this.findById(postId);

        return PostResponseDto.of(post);
    }

    public List<PostResponseDto> getPosts(){

        List<Post> entityList = postRepository.findAll();
        List<PostResponseDto> dtoList = new ArrayList<>();

        for(Post e: entityList) dtoList.add(PostResponseDto.of(e));

        return dtoList;
    }

    @Transactional
    public Boolean deletePost(Long postId, String email){

        Post post = this.findById(postId);
        User user = userService.findByEmail(email);
        Thread findThread = threadService.findById(post.getThread().getThreadId());

        if(!user.getUserId().equals(post.getAuthor().getUserId())){
            throw new AppException(ErrorCode.UNAUTHORIZED_TRIAL, "작성자만 삭제 가능합니다.");
        }

        user.getPosts().remove(post);
        findThread.getPosts().remove(post);
        bookmarkRepository.deleteAllByPostId(postId);
        postRepository.delete(post);

        return true;
    }

    public Post findById(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElseThrow(() -> new NoSuchElementException("no such post with the id"));
    }
}
