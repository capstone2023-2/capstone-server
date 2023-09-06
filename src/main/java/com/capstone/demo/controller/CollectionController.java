package com.capstone.demo.controller;

import com.capstone.demo.model.dto.request.CollectionRequestDto;
import com.capstone.demo.model.dto.response.BaseResponseDto;
import com.capstone.demo.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> createCollection(@RequestBody CollectionRequestDto collectionRequestDto){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.CREATED.value(),
                        "collection successfully created!",
                        collectionService.createCollection(collectionRequestDto)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getCollections(){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "all collections data successfully received!",
                        collectionService.getCollections()), HttpStatus.OK);
    }

    @PostMapping("/{collectionId}/threads/{threadId}")
    public ResponseEntity<BaseResponseDto> addThreadToCollection(@PathVariable Long collectionId,
                                                                      @PathVariable Long threadId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "thread successfully added to this collection",
                        collectionService.addThreadToCollection(collectionId, threadId)), HttpStatus.OK);
    }

    @DeleteMapping("/{collectionId}/threads/{threadId}")
    public ResponseEntity<BaseResponseDto> deleteThreadFromCollection(@PathVariable Long collectionId,
                                                                      @PathVariable Long threadId){

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "thread successfully deleted from this collection",
                        collectionService.deleteThreadFromCollection(collectionId, threadId)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteCollectionById(@PathVariable Long id) {

        return new ResponseEntity<>(
                new BaseResponseDto(
                        HttpStatus.OK.value(),
                        "collection successfully deleted",
                        collectionService.deleteCollection(id)), HttpStatus.OK);
    }
}
