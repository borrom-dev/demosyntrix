package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.domain.Page;
import com.angkorsuntrix.demosynctrix.domain.Post;
import com.angkorsuntrix.demosynctrix.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/pages")
    public HttpEntity create(@RequestBody Post post) {
        final Post createdPost = repository.save(post);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @PutMapping("/pages")
    public HttpEntity update(@RequestBody Post post) {
        final Post newPost = repository.save(post);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    public HttpEntity delete(@RequestBody Post post) {
        repository.delete(post);
        return new ResponseEntity(HttpStatus.OK);
    }
}
