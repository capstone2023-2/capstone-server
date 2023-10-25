package com.capstone.demo.controller;

import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.model.dto.response.PostResponseDto;
import com.capstone.demo.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/bookmarks")
@Tag(name = "북마크 기능", description = "BookmarkController")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크에 포스트 추가", description = "사용자의 북마크에 특정 포스트를 추가합니다.", tags = "북마크 기능")
    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponseDto<Boolean>> addBookmark(@PathVariable Long postId,
                                                                Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "post successfully bookmarked!",
                        bookmarkService.addBookmark(postId, authentication.getName())), HttpStatus.OK);
    }

    @Operation(summary = "북마크에서 포스트 삭제", description = "사용자의 북마크에서 특정 포스트를 삭제합니다.", tags = "북마크 기능")
    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponseDto<Boolean>> removeBookmark(@PathVariable Long postId,
                                                                   Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "post successfully un-bookmarked!",
                        bookmarkService.removeBookmark(postId, authentication.getName())), HttpStatus.OK);
    }

    @Operation(summary = "북마크 불러오기", description = "사용자의 북마크 포스트들을 불러옵니다.", responses = {
            @ApiResponse(responseCode = "200", description = "북마크한 포스트 목록 반환",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponseDto.class))))
    }, tags = "북마크 기능")
    @GetMapping()
    public ResponseEntity<BaseResponseDto<List<PostResponseDto>>> getBookmark(Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "bookmark data successfully received!",
                        bookmarkService.getBookmark(authentication.getName())), HttpStatus.OK);
    }
}
