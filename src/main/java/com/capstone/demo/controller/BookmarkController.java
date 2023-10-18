package com.capstone.demo.controller;

import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크에 포스트 추가", description = "사용자의 북마크에 특정 포스트를 추가합니다.")
    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponseDto> bookmarkPost(@PathVariable Long postId,
                                                        Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "post successfully bookmarked!",
                        bookmarkService.bookmarkPost(postId, authentication.getName())), HttpStatus.OK);
    }

    @Operation(summary = "북마크에서 포스트 삭제", description = "사용자의 북마크에서 특정 포스트를 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponseDto> unbookmarkPost(@PathVariable Long postId,
                                                        Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "post successfully un-bookmarked!",
                        bookmarkService.unbookmarkPost(postId, authentication.getName())), HttpStatus.OK);
    }

    @Operation(summary = "북마크 불러오기", description = "사용자의 북마크 포스트들을 불러옵니다.")
    @GetMapping()
    public ResponseEntity<BaseResponseDto> getBookmark(Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "bookmark data successfully received!",
                        bookmarkService.getBookmark(authentication.getName())), HttpStatus.OK);
    }
}
