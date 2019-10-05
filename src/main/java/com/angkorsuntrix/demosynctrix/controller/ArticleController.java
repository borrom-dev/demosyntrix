package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.entity.Article;
import com.angkorsuntrix.demosynctrix.entity.Topic;
import com.angkorsuntrix.demosynctrix.entity.User;
import com.angkorsuntrix.demosynctrix.exception.ResourceNotFoundException;
import com.angkorsuntrix.demosynctrix.mapping.Pager;
import com.angkorsuntrix.demosynctrix.payload.ApiResponse;
import com.angkorsuntrix.demosynctrix.payload.ArticleRequest;
import com.angkorsuntrix.demosynctrix.payload.ArticleResponse;
import com.angkorsuntrix.demosynctrix.repository.ArticleRepository;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import com.angkorsuntrix.demosynctrix.repository.UserRepository;
import com.angkorsuntrix.demosynctrix.security.CurrentUser;
import com.angkorsuntrix.demosynctrix.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/articles/id/{id}")
    public HttpEntity getAll(@PathVariable(value = "id") Long id) {
        return articleRepository.findById(id).map(article -> {
            final ArticleResponse response = new ArticleResponse(article);
            return ResponseEntity.ok(response);
        }).orElseThrow(() -> new ResourceNotFoundException("Article not found"));
    }


    @GetMapping("/articles/published/{id}")
    public HttpEntity getAllByPublished(@PathVariable(value = "id") Long id, final Pageable pageable) {
        final Page<ArticleResponse> articlePage = articleRepository
                .findByTopicIdAndPublished(id, true, pageable)
                .map(ArticleResponse::new);
        return ResponseEntity.ok(new Pager<>(articlePage.getContent(), articlePage.getSize(), articlePage.getTotalPages()));
    }

    @GetMapping("/articles")
    public HttpEntity getAll(final Pageable pageable) {
        final Page<ArticleResponse> articlePage = articleRepository.findAll(pageable).map(ArticleResponse::new);
        return ResponseEntity.ok(new Pager<>(articlePage.getContent(), articlePage.getSize(), articlePage.getTotalPages()));
    }

    @GetMapping("/articles/recent")
    public HttpEntity getRecentPost() {
        final Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "createBy");
        final Page<ArticleResponse> responses = articleRepository.findAll(pageable).map(ArticleResponse::new);
        return ResponseEntity.ok(new Pager<>(responses.getContent(), responses.getSize(), responses.getTotalPages()));
    }

    @GetMapping("/articles/published_recent")
    public HttpEntity getHomeArticles() {
        final Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "createBy");
        final Page<ArticleResponse> responses = articleRepository.findByPublished(true, pageable).map(ArticleResponse::new);
        return ResponseEntity.ok(new Pager<>(responses.getContent(), responses.getSize(), responses.getTotalPages()));
    }

    @GetMapping("/articles/{topic_id}")
    public HttpEntity getArticlesByTopic(@PathVariable(value = "topic_id") Long topicId, Pageable pageable) {
        final Page<ArticleResponse> articles = articleRepository.findByTopicId(topicId, pageable).map(ArticleResponse::new);
        return ResponseEntity.ok(new Pager<>(articles.getContent(), articles.getSize(), articles.getTotalPages()));
    }

    @PostMapping("/articles/{topic_id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public HttpEntity create(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "topic_id") long topicId, @Valid @RequestBody ArticleRequest request) {
        final User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found: " + currentUser.getId()));
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
        final User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found: " + currentUser.getId()));
        final Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));
        return articleRepository.findById(request.getId()).map(article -> {
            request.setTopic(topic);
            article.update(user, request);
            final Article newArticle = articleRepository.save(article);
            return ResponseEntity.ok(new ArticleResponse(newArticle));
        }).orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + request.getId()));
    }

    @DeleteMapping("/posts")
    public HttpEntity delete(@RequestBody Article article) {
        articleRepository.delete(article);
        return new ResponseEntity(HttpStatus.OK);
    }
}
