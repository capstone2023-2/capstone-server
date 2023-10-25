package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.CollectionRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.model.dto.response.CollectionResponseDto;
import com.capstone.demo.model.dto.response.CommentResponseDto;
import com.capstone.demo.service.CollectionService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "컬렉션 기능", description = "CollectionController")
public class CollectionController {

    private final CollectionService collectionService;

    @Operation(summary = "컬렉션 생성", description = "새로운 컬렉션을 생성합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "컬렉션 생성 성공, 컬렉션 정보 반환",
                    content = @Content(schema = @Schema(implementation = CollectionResponseDto.class)))
    }, tags = "컬렉션 기능")
    @PostMapping("/collections")
    public ResponseEntity<BaseResponseDto<CollectionResponseDto>> createCollection(@RequestBody CollectionRequestDto collectionRequestDto,
                                                                                   Authentication authentication){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "collection successfully created!",
                        collectionService.createCollection(collectionRequestDto, authentication.getName())), HttpStatus.CREATED);
    }

    @Operation(summary = "특정 유저의 컬렉션 조회", description = "특정 유저가 생성한 모든 컬렉션을 userId를 이용하여 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "특정 유저의 컬렉션 목록 반환",
                    content =  @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionResponseDto.class))))
    }, tags = "컬렉션 기능")
    @GetMapping("/users/{userId}/collections")
    public ResponseEntity<BaseResponseDto<List<CollectionResponseDto>>> getCollectionsOfUser(@PathVariable Long userId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all collections data successfully received!",
                        collectionService.getCollectionsOfUser(userId)), HttpStatus.OK);
    }

    @Operation(summary = "모든 컬렉션 조회", description = "생성된 모든 컬렉션을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "전체 컬렉션 목록 반환",
                    content =  @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionResponseDto.class))))
    }, tags = "컬렉션 기능")
    @GetMapping("/collections")
    public ResponseEntity<BaseResponseDto<List<CollectionResponseDto>>> getCollections(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all collections data successfully received!",
                        collectionService.getCollections()), HttpStatus.OK);
    }

    @Operation(summary = "특정 컬렉션 조회", description = "특정 컬렉션을 collectionId를 이용하여 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "컬렉션 정보 하나 반환",
                    content = @Content(schema = @Schema(implementation = CollectionResponseDto.class)))
    }, tags = "컬렉션 기능")
    @GetMapping("/collections/{collectionId}")
    public ResponseEntity<BaseResponseDto<CollectionResponseDto>> getCollection(@PathVariable Long collectionId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all collections data successfully received!",
                        collectionService.getCollection(collectionId)), HttpStatus.OK);
    }

    @Operation(summary = "컬렉션에 스레드 추가", description = "특정 컬렉션에 특정 스레드를 collectionId와 threadId를 이용하여 추가합니다.",
            tags = "컬렉션 기능")
    @PostMapping("/collections/{collectionId}/threads/{threadId}")
    public ResponseEntity<BaseResponseDto> addThreadToCollection(@PathVariable Long collectionId,
                                                                 @PathVariable Long threadId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "thread successfully added to this collection",
                        collectionService.addThreadToCollection(collectionId, threadId)), HttpStatus.OK);
    }

    @Operation(summary = "컬렉션에서 스레드 삭제", description = "특정 컬렉션의 특정 스레드를 collectionId와 threadId를 이용하여 컬렉션으로부터 제거합니다.",
            tags = "컬렉션 기능")
    @DeleteMapping("/collections/{collectionId}/threads/{threadId}")
    public ResponseEntity<BaseResponseDto> deleteThreadFromCollection(@PathVariable Long collectionId,
                                                                      @PathVariable Long threadId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "thread successfully deleted from this collection",
                        collectionService.deleteThreadFromCollection(collectionId, threadId)), HttpStatus.OK);
    }

    @Operation(summary = "컬렉션 삭제", description = "특정 컬렉션을 collectionId를 이용하여 삭제합니다.",
            tags = "컬렉션 기능")
    @DeleteMapping("/collections/{collectionId}")
    public ResponseEntity<BaseResponseDto> deleteCollectionById(@PathVariable Long collectionId) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "collection successfully deleted",
                        collectionService.deleteCollection(collectionId)), HttpStatus.OK);
    }
}
