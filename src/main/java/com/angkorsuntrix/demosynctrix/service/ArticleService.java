package com.angkorsuntrix.demosynctrix.service;

import com.angkorsuntrix.demosynctrix.domain.Article;
import com.angkorsuntrix.demosynctrix.domain.ArticleResponse;
import com.angkorsuntrix.demosynctrix.domain.Topic;
import com.angkorsuntrix.demosynctrix.mapping.Pager;
import com.angkorsuntrix.demosynctrix.repository.ArticleRepository;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository, final TopicRepository topicRepository) {
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
    }

    public Pager<ArticleResponse> getArticles(final long topicId, Pageable pageable) {
        final Pager<ArticleResponse> articlePage = new Pager<>();
        final Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            final Page<Article> articles = articleRepository.findByTopicId(topicId, pageable);
            articles.get().forEach(new Consumer<Article>() {
                @Override
                public void accept(Article article) {
                    articlePage.getData().add(new ArticleResponse(article, optionalTopic.get()));
                }
            });
            articlePage.setSize(articles.getSize());
            articlePage.setTotalPage(articles.getTotalPages());
        }
        return articlePage;
    }

    public Pager<Article> getArticles(Pageable pageable) {
        final Page<Article> articlePage =  articleRepository.findAll(pageable);
        return new Pager<>(articlePage.getContent(), articlePage.getSize(), articlePage.getTotalPages());
    }
}
