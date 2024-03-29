package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.entity.Topic;
import com.angkorsuntrix.demosynctrix.exception.ResourceNotFoundException;
import com.angkorsuntrix.demosynctrix.payload.ApiResponse;
import com.angkorsuntrix.demosynctrix.payload.TopicRequest;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicRepository repository;

    @GetMapping("/topics")
    public HttpEntity getAll() {
        final List<Topic> topics = repository.findAll(new Sort(Sort.Direction.ASC, "position"));
        return ResponseEntity.ok(topics);
    }


    @GetMapping("/topics/active")
    public HttpEntity getActiveTopics() {
        final List<Topic> topics = repository.findByStatus(true, new Sort(Sort.Direction.ASC, "position"));
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/topics/{topic_id}")
    public HttpEntity getTopicById(@PathVariable("topic_id") Long topicId) {
        return repository.findById(topicId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot topic id"));
    }

    @PostMapping("/topics")
    public HttpEntity create(@Valid @RequestBody TopicRequest request) {
        Topic topic = repository.save(new Topic(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{topicId}")
                .buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Topic created successfully"));
    }

    @PutMapping("/topics/{topic_id}")
    public HttpEntity update(@PathVariable("topic_id") Long topicId, @Valid @RequestBody TopicRequest request) {
        return repository.findById(topicId).map(topic -> {
            topic.update(request);
            final Topic newTopic = repository.save(topic);
            return ResponseEntity.ok(newTopic);
        }).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));
    }

    @DeleteMapping("/topics/{topic_id}")
    public HttpEntity delete(@PathVariable("topic_id") Long id) {
        return repository.findById(id).map(topic -> {
            repository.delete(topic);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + id));
    }
}
