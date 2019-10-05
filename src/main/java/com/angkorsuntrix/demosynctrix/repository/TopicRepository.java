package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.entity.Topic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByStatus(boolean status, Sort orders);
}
