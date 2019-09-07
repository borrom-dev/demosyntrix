package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.domain.Page;
import com.angkorsuntrix.demosynctrix.domain.Post;
import com.angkorsuntrix.demosynctrix.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository repository;

    @GetMapping("/posts")
    public HttpEntity getAll() {
        final List<Post> posts = repository.getAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/posts")
    public HttpEntity create(@RequestBody Post post) {
        final Post createdPost = repository.save(post);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @PutMapping("/posts")
    public HttpEntity update(@RequestBody Post post) {
        final Post newPost = repository.save(post);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @DeleteMapping("/posts")
    public HttpEntity delete(@RequestBody Post post) {
        repository.delete(post);
        return new ResponseEntity(HttpStatus.OK);
    }
}
