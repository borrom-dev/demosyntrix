package com.angkorsuntrix.demosynctrix.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String slug;
    private String body;
    private boolean published;
    private Date publish_at;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    @JsonIgnore
    private Page page;

    public Post(String title, String slug, String body, boolean published, Date publish_at) {
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
}
