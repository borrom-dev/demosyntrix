package com.angkorsuntrix.demosynctrix.service;

import com.angkorsuntrix.demosynctrix.domain.Article;
import com.angkorsuntrix.demosynctrix.domain.ArticleResponse;
import com.angkorsuntrix.demosynctrix.domain.Topic;
import com.angkorsuntrix.demosynctrix.mapping.Pager;
import com.angkorsuntrix.demosynctrix.repository.ArticleRepository;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository, final TopicRepository topicRepository) {
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
    }

    public Pager<Article> getArticles(final long topicId, Pageable pageable) {
        final Pager<Article> articlePage = new Pager<>();
            final Page<Article> articles = articleRepository.findByTopicId(topicId, pageable);
            articlePage.setSize(articles.getSize());
            articlePage.setData(articles.getContent());
            articlePage.setTotalPage(articles.getTotalPages());
        return articlePage;
    }

    public Pager<Article> getArticles(Pageable pageable) {
        final Page<Article> articlePage = articleRepository.findAll(pageable);
        return new Pager<>(articlePage.getContent(), articlePage.getSize(), articlePage.getTotalPages());
    }
}
