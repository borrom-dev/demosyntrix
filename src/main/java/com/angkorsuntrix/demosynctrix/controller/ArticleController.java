package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.entity.Article;
import com.angkorsuntrix.demosynctrix.entity.Topic;
import com.angkorsuntrix.demosynctrix.entity.User;
import com.angkorsuntrix.demosynctrix.exception.ResourceNotFoundException;
import com.angkorsuntrix.demosynctrix.mapping.Pager;
import com.angkorsuntrix.demosynctrix.payload.ApiResponse;
import com.angkorsuntrix.demosynctrix.payload.ArticleRequest;
import com.angkorsuntrix.demosynctrix.repository.ArticleRepository;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import com.angkorsuntrix.demosynctrix.repository.UserRepository;
import com.angkorsuntrix.demosynctrix.security.CurrentUser;
import com.angkorsuntrix.demosynctrix.security.UserPrincipal;
import com.angkorsuntrix.demosynctrix.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/articles")
    public HttpEntity getAll(final Pageable pageable) {
        final Page<Article> articlePage = articleRepository.findAll(pageable);
        return ResponseEntity.ok(new Pager<>(articlePage.getContent(), articlePage.getSize(), articlePage.getTotalPages()));
    }

    @GetMapping("/articles/recent")
    public HttpEntity getRecentPost() {
        return ResponseEntity.ok(articleRepository.findAll());
    }

    @GetMapping("/articles/{topic_id}")
    public HttpEntity getArticlesByTopic(@PathVariable(value = "topic_id") Long topicId, Pageable pageable) {
        final Page<Article> articles = articleRepository.findByTopicId(topicId, pageable);
        return ResponseEntity.ok(new Pager<>(articles.getContent(), articles.getSize(), articles.getTotalPages()));
    }

    @PostMapping("/articles/{topic_id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public HttpEntity create(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "topic_id") long topicId, @Valid @RequestBody ArticleRequest request) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found: " + currentUser.getId()));
        return topicRepository.findById(topicId)
                .map(topic -> {
                    request.setTopic(topic);
                    Article article = articleRepository.save(new Article(user, request));
                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{articleId}")
                            .buildAndExpand(article.getId()).toUri();
                    return ResponseEntity.created(location)
                            .body(new ApiResponse(true, "Article created successfully"));
                }).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));
    }

    @PutMapping("/articles/{topic_id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public HttpEntity update(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "topic_id") Long topicId, @Valid @RequestBody ArticleRequest request) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found: " + currentUser.getId()));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));
        return articleRepository.findById(request.getId()).map(article -> {
            request.setTopic(topic);
            article.update(user, request);
            Article newArticle = articleRepository.save(article);
            return ResponseEntity.ok(newArticle);
        }).orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + request.getId()));
    }

    @DeleteMapping("/posts")
    public HttpEntity delete(@RequestBody Article article) {
        articleRepository.delete(article);
        return new ResponseEntity(HttpStatus.OK);
    }
}
