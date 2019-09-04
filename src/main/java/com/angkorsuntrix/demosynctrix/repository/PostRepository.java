package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(value = "SELECT * FROM post", nativeQuery = true)
    List<Post> getAll();
}
