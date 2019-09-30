package com.angkorsuntrix.demosynctrix.entity;

import com.angkorsuntrix.demosynctrix.entity.audit.UserAudit;
import com.angkorsuntrix.demosynctrix.payload.ArticleRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "articles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "title",
                "slug"
        })
})
public class Article extends UserAudit {

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "attribute_value_range", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "range_id")
    @Column(name = "value")
    private Map<String, String> metaMap = new HashMap<>();

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

    public Article(User user, ArticleRequest request) {
        this.title = request.getTitle();
        this.slug = request.getSlug();
        this.body = request.getBody();
        this.description = request.getDescription();
        this.topic = request.getTopic();
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, String> getMetaMap() {
        return metaMap;
    }

    public void setMetaMap(Map<String, String> metaMap) {
        this.metaMap = metaMap;
    }

    public void update(User user, ArticleRequest request) {
        this.user = user;
        this.topic = request.getTopic();
        this.title = request.getTitle();
        this.slug = request.getSlug();
        this.body = request.getBody();
        this.description = request.getDescription();
        this.metaMap = request.getMetaMap();
    }
}
