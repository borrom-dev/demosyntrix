package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.domain.Page;
import com.angkorsuntrix.demosynctrix.domain.Post;
import com.angkorsuntrix.demosynctrix.repository.PageRepository;
import com.angkorsuntrix.demosynctrix.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class PostController {

    @Autowired
    private PostRepository repository;
    @Autowired
    private PageRepository pageRepository;

    @GetMapping("/posts")
    public org.springframework.data.domain.Page<Post> getAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/posts/recent")
    public HttpEntity getRecentPost() {
        final List<Post> posts = new ArrayList<>();
        repository.findAll().forEach(posts::add);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts?topic={topic_id}")
    public org.springframework.data.domain.Page<Post> getArticlesByTopic(@Param(value = "topic_id") Long id, Pageable pageable) {
        return repository.findByPageId(id, pageable);
    }

    @PostMapping("/posts/{page_id}")
    public HttpEntity create(@PathVariable(value = "page_id") long pageId, @RequestBody @Valid Post post) {
        final Optional<Post> value = pageRepository.findById(pageId).flatMap((Function<Page, Optional<Post>>) page -> {
            post.setPage(page);
            final Post value1 = repository.save(post);
            return Optional.of(value1);
        });
        if (value.isPresent()) {
            return ResponseEntity.ok(value.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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
