package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.entity.Topic;
import com.angkorsuntrix.demosynctrix.payload.ApiResponse;
import com.angkorsuntrix.demosynctrix.payload.TopicRequest;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicRepository repository;

    @GetMapping("/pages")
    public HttpEntity getAll() {
        final List<Topic> topics = new ArrayList<>();
        repository.findAll().forEach(topics::add);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PostMapping("/pages")
    public HttpEntity create(@Valid @RequestBody TopicRequest request) {
        Topic topic = repository.save(new Topic(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{topicId}")
                .buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Topic created successfully"));
    }

    @PutMapping("/pages")
    public HttpEntity update(@RequestBody Topic topic) {
        final Topic newTopic = repository.save(topic);
        return new ResponseEntity<>(newTopic, HttpStatus.OK);
    }

    @DeleteMapping("/pages")
    public HttpEntity delete(@RequestBody Topic topic) {
        repository.delete(topic);
        return new ResponseEntity(HttpStatus.OK);
    }
}
