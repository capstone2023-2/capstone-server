package com.capstone.demo.service;

import com.capstone.demo.model.domain.Collection;
import com.capstone.demo.model.domain.Thread;
import com.capstone.demo.model.domain.User;
import com.capstone.demo.model.dto.request.CollectionRequestDto;
import com.capstone.demo.model.dto.response.CollectionResponseDto;
import com.capstone.demo.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserService userService;
    private final ThreadService threadService;

    public Boolean createCollection(CollectionRequestDto collectionRequestDto){

        User findUser = userService.findById(collectionRequestDto.getUserId());

        Collection collection = Collection.builder()
                .author(findUser)
                .name(collectionRequestDto.getName())
                .threads(new ArrayList<>())
                .build();

        findUser.getCollections().add(collection);
        collectionRepository.save(collection);

        return true;
    }

    public List<CollectionResponseDto> getCollections(){

        List<Collection> entityList = collectionRepository.findAll();
        List<CollectionResponseDto> dtoList = new ArrayList<>();

        for (Collection e : entityList) dtoList.add(CollectionResponseDto.of(e));

        return dtoList;
    }

    public Boolean deleteCollection(Long collectionId){

        Collection findCollection = this.findById(collectionId);
        User findUser = userService.findById(findCollection.getAuthor().getUserId());

        findUser.getCollections().remove(findCollection);
        collectionRepository.delete(findCollection);

        return true;
    }

    public Boolean addThreadToCollection(Long collectionId, Long threadId) { // postman 테스트 제대로 동작 안함

        Collection findCollection = this.findById(collectionId);
        Thread findThread = threadService.findById(threadId);

        if(findCollection.getThreads().contains(findThread))    return false;

        findCollection.getThreads().add(findThread);
        collectionRepository.save(findCollection); // 다른 경우에는 이렇게 직접 .save 를 사용하지 않아도 영속화 잘 됨

        return true;
    }

    public Boolean deleteThreadFromCollection(Long collectionId, Long threadId){

        Collection findCollection = this.findById(collectionId);
        Thread findThread = threadService.findById(threadId);

        if(!findCollection.getThreads().contains(findThread))   return false;

        findCollection.getThreads().remove(findThread);
        collectionRepository.save(findCollection);

        return true;
    }

    public Collection findById(Long collectionId){
        Optional<Collection> optionalCollection = collectionRepository.findById(collectionId);
        return optionalCollection.orElseThrow(() -> new NoSuchElementException("no such collection with the id(" + collectionId + ")"));
    }
}
