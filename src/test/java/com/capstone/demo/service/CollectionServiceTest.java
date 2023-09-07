package com.capstone.demo.service;

import com.capstone.demo.model.domain.Collection;
import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.CollectionRequestDto;
import com.capstone.demo.repository.CollectionRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @InjectMocks
    CollectionService collectionService;
    @Mock
    CollectionRepository collectionRepository;
    @Mock
    ThreadService threadService;

    private static Collection collection;
    private static Thread thread;
    private static Post post;
    private static User user;

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

        post = Post.builder()
                .postId(1L)
                .author(user)
                .thread(thread)
                .title("tmp Post")
                .content("테스트용 임시 포스트입니다")
                .views(3)
                .comments(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        user.getPosts().add(post);
        thread.getPosts().add(post);

        collection = Collection.builder()
                .collectionId(1L)
                .author(user)
                .name("test collection")
                .threads(new ArrayList<>())
                .build();

        user.getCollections().add(collection);

    }

    @Test
    @DisplayName("컬렉션에 스레드가 성공적으로 저장되는지 확인")
    void addThreadToCollection() {

        // given
        Long collectionId = 1L;
        Long threadId = 1L;
        given(collectionRepository.findById(collectionId)).willReturn(Optional.of(collection));
        given(threadService.findById(threadId)).willReturn(thread);

        // when
        Boolean result = collectionService.addThreadToCollection(collectionId, threadId);

        // then
        assertThat(collection.getThreads().size()).isEqualTo(1);
        assertThat(collection.getThreads().get(0).getName()).isEqualTo("thread1");
    }
}