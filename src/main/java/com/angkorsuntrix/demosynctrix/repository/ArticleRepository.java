package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.entity.Article;
import com.angkorsuntrix.demosynctrix.payload.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByTopicId(Long topicId, Pageable pageable);

    Page<Article> findByTopicIdAndPublished(Long topicId, boolean published, Pageable pageable);

    Page<Article> findByPublished(boolean published, Pageable pageable);

}
