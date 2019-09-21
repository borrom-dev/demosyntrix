package com.angkorsuntrix.demosynctrix.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String slug;
    private String body;
    private boolean published;
    private Date publish_at;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    private Topic topic;

    public Article() {
    }

    public Article(String title, String slug, String body, boolean published, Date publish_at) {
        this.title = title;
        this.slug = slug;
        this.body = body;
        this.published = published;
        this.publish_at = publish_at;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Date getPublish_at() {
        return publish_at;
    }

    public void setPublish_at(Date publish_at) {
        this.publish_at = publish_at;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void from(Article article) {
        this.title = article.title;
        this.slug = article.slug;
        this.body = article.body;
        this.published = article.published;
        this.publish_at = article.publish_at;
    }
}
