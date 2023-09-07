package com.capstone.demo.service;

import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.PostRequestDto;
import com.capstone.demo.model.dto.response.PostResponseDto;
import com.capstone.demo.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private static PostRequestDto requestDto;
    private static User user;
    private static Thread thread;
    private static Post alreadyAddedPost;

    @InjectMocks
    PostService postService;
    @Mock
    PostRepository postRepository;
    @Mock
    UserService userService;
    @Mock
    ThreadService threadService;

    @BeforeAll
    static void setUp(){

        user = User.builder()
                .userId(1L)
                .email("email")
                .password("pw")
                .username("flash")
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .threads(new ArrayList<>())
                .collections(new ArrayList<>())
                .build();

        thread = Thread.builder()
                .threadId(1L)
                .author(user)
                .name("thread1")
                .description("test 스레드입니다")
                .posts(new ArrayList<>())
                .build();

        user.getThreads().add(thread);

        alreadyAddedPost = Post.builder()
                .postId(1L)
                .author(user)
                .thread(thread)
                .title("tmp Post")
                .content("테스트용 임시 포스트입니다")
                .views(3)
                .comments(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        user.getPosts().add(alreadyAddedPost);
        thread.getPosts().add(alreadyAddedPost);

        requestDto = PostRequestDto.builder()
                .title("request dto")
                .content("테스트용 포스트 요청 dto입니다")
                .userId(1L)
                .threadId(1L)
                .build();
    }

    @Test
    @DisplayName("Post를 새로 생성할 때 User와 Thread의 List<Post> 에도 제대로 추가되는지")
    void createPost() {

        //given
        Long userId = 1L;
        Long threadId = 1L;
        given(userService.findById(userId)).willReturn(user);
        given(threadService.findById(threadId)).willReturn(thread);

        //when
        PostResponseDto responseDto = postService.createPost(requestDto);

        //then
        assertThat(responseDto.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(requestDto.getContent());

        assertThat(user.getPosts().size()).isEqualTo(2);
        assertThat(thread.getPosts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Post를 삭제할 때 User와 Thread의 List<Post> 로부터도 잘 삭제되는지")
    void deletePost() {

        // given
        Long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.of(alreadyAddedPost));
        given(userService.findById(alreadyAddedPost.getAuthor().getUserId())).willReturn(user);
        given(threadService.findById(alreadyAddedPost.getThread().getThreadId())).willReturn(thread);

        // when
        postService.deletePost(postId);

        // then
        assertThat(user.getPosts()).isEmpty();
        assertThat(thread.getPosts()).isEmpty();
    }
}