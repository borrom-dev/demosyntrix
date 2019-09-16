package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByTopicId(Long topicId, Pageable pageable);

}
