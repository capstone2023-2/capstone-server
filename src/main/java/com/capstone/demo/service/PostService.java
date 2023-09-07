package com.capstone.demo.service;

import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.PostRequestDto;
import com.capstone.demo.model.dto.response.PostResponseDto;
import com.capstone.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ThreadService threadService;

    public PostResponseDto createPost(PostRequestDto postRequestDto) {

        User findUser = userService.findById(postRequestDto.getUserId());
        Thread findThread = threadService.findById(postRequestDto.getThreadId());

        Post post = Post.builder()
                .author(findUser)
                .thread(findThread)
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .views(0)
                .comments(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        findUser.getPosts().add(post);
        findThread.getPosts().add(post);
        postRepository.save(post);

        return PostResponseDto.of(post);
    }

    public List<PostResponseDto> getPosts(){

        List<Post> entityList = postRepository.findAll();
        List<PostResponseDto> dtoList = new ArrayList<>();

        for(Post e: entityList) dtoList.add(PostResponseDto.of(e));

        return dtoList;
    }

    public Boolean deletePost(Long postId){

        Post findPost = this.findById(postId);
        User findUser = userService.findById(findPost.getAuthor().getUserId());
        Thread findThread = threadService.findById(findPost.getThread().getThreadId());

        findUser.getPosts().remove(findPost);
        findThread.getPosts().remove(findPost);
        postRepository.delete(findPost);

        return true;
    }

    public Post findById(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElseThrow(() -> new NoSuchElementException("no such post with the id"));
    }
}
