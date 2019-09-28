package com.angkorsuntrix.demosynctrix.payload;

import com.angkorsuntrix.demosynctrix.entity.Article;
import com.angkorsuntrix.demosynctrix.entity.Topic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ArticleResponse {

    private Long id;
    private String title;
    private String slug;
    private String description;
    private String body;
    private boolean published;
    @JsonFormat(pattern="dd MMMM yyyy")
    @JsonProperty("create_at")
    private Date createAt;
    @JsonFormat(pattern="dd MMMM yyyy")
    @JsonProperty("update_at")
    private Date updateAt;
    @JsonProperty("topic_id")
    private long topicId;

    public ArticleResponse(final Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.slug = article.getSlug();
        this.description = article.getDescription();
        this.body = article.getBody();
        this.published = article.isPublished();
        this.createAt = article.getCreatedAt();
        this.updateAt = article.getUpdatedAt();
        this.topicId = article.getTopic().getId();
    }

    public ArticleResponse(Long id, String title, String slug, String body, boolean published) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.body = body;
        this.published = published;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }
}
