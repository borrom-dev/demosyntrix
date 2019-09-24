package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.entity.Topic;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PageController {

    @Autowired
    private TopicRepository repository;

    @GetMapping("/pages")
    public HttpEntity getAll() {
        final List<Topic> topics = new ArrayList<>();
        repository.findAll().forEach(topics::add);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PostMapping("/pages")
    public HttpEntity create(@RequestBody Topic topic) {
        final Topic createdTopic = repository.save(topic);
        return new ResponseEntity<>(createdTopic, HttpStatus.OK);
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
