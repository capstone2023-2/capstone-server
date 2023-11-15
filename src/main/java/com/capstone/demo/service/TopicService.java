package com.capstone.demo.service;

import com.capstone.demo.model.domain.Topic;
import com.capstone.demo.model.dto.TopicDto;
import com.capstone.demo.repository.TopicRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public List<TopicDto> getTopics(){
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topic -> new TopicDto(topic.getTopic(), topic.getUri()))
                .collect(Collectors.toList());
    }
}