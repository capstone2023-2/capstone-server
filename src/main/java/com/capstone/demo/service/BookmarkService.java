package com.capstone.demo.service;

import com.capstone.demo.model.domain.Bookmark;
import com.capstone.demo.model.domain.Post;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.response.PostResponseDto;
import com.capstone.demo.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostService postService;
    private final UserService userService;

    public Boolean addBookmark(Long postId, String email){

        User user = userService.findByEmail(email);
        Post post = postService.findById(postId);

        Bookmark bookmark = Bookmark.builder()
                .postId(postId)
                .userId(user.getUserId())
                .build();

        post.addBookmark();
        bookmarkRepository.save(bookmark);

        return true;
    }

    @Transactional
    public Boolean removeBookmark(Long postId, String email){

        User user = userService.findByEmail(email);
        Post post = postService.findById(postId);

        post.removeBookmark();
        Integer delete = bookmarkRepository.deleteByUserIdAndPostId(user.getUserId(), postId);
        return delete == 1;
    }

    public List<PostResponseDto> getBookmark(String email){

        User user = userService.findByEmail(email);
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserId(user.getUserId());
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for(Bookmark bookmark: bookmarkList){
            Post post = postService.findById(bookmark.getPostId());
            postResponseDtoList.add(PostResponseDto.of(post));
        }

        return postResponseDtoList;
    }
}
