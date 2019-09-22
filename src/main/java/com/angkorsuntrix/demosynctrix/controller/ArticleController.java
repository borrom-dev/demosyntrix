package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.domain.Article;
import com.angkorsuntrix.demosynctrix.domain.ArticleResponse;
import com.angkorsuntrix.demosynctrix.domain.Topic;
import com.angkorsuntrix.demosynctrix.mapping.Pager;
import com.angkorsuntrix.demosynctrix.repository.ArticleRepository;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import com.angkorsuntrix.demosynctrix.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepository repository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/posts")
    public HttpEntity getAll(final Pageable pageable) {
        final Pager<Article> pager =  articleService.getArticles(pageable);
        return ResponseEntity.ok(pager);
    }

    @GetMapping("/posts/recent")
    public HttpEntity getRecentPost() {
        final List<Article> articles = new ArrayList<>(repository.findAll());
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/posts?topic={topic_id}")
    public HttpEntity getArticlesByTopic(@Param(value = "topic_id") Long id, Pageable pageable) {
        Pager<ArticleResponse> pager = articleService.getArticles(id, pageable);
        return ResponseEntity.ok(pager);
    }

    @PostMapping("/posts/{page_id}")
    public HttpEntity create(@PathVariable(value = "page_id") long pageId, @RequestBody @Valid Article article) {
        final Optional<Article> value = topicRepository.findById(pageId).flatMap((Function<Topic, Optional<Article>>) topic -> {
            article.setTopic(topic);
            final Article value1 = repository.save(article);
            return Optional.of(value1);
        });
        if (value.isPresent()) {
            return ResponseEntity.ok(value.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/posts")
    public HttpEntity update(@RequestBody Article article) {
        final Topic topic = article.getTopic();
        final Optional<Article> optional = repository.findById(article.getId());
        if (optional.isPresent()) {
            final Article updateArticle = optional.get();
            updateArticle.from(article);
            final Article newArticle = repository.save(updateArticle);
            return new ResponseEntity<>(newArticle, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/posts")
    public HttpEntity delete(@RequestBody Article article) {
        repository.delete(article);
        return new ResponseEntity(HttpStatus.OK);
    }
}
