package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
