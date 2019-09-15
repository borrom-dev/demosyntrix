package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByPageId(Long topicId, Pageable pageable);

}
