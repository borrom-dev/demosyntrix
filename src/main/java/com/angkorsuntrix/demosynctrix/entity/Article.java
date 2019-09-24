package com.angkorsuntrix.demosynctrix.entity;

import com.angkorsuntrix.demosynctrix.exception.EntityType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(min = 8, max = 100)
    private String title;
    @NotNull
    @Size(min = 8, max = 100)
    private String slug;
    @Column(columnDefinition = "text")
    private String body;
    @NotBlank
    @Size(min = 200)
    private String description;
    private boolean published;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    private Topic topic;
    @ManyToMany(cascade = CascadeType.ALL)
    private Map<String, EntityType> metaMap = new HashMap<>();

    public Article() {
    }

    public Article(Long id, String title, String slug, String body, String description, boolean published) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.body = body;
        this.description = description;
        this.published = published;
    }

    public void from(Article article) {
        this.title = article.title;
        this.slug = article.slug;
        this.body = article.body;
        this.published = article.published;
        this.topic = article.topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
