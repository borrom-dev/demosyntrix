package com.angkorsuntrix.demosynctrix.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String slug;
    private String body;
    private Date publish_at;

    public Post(String title, String slug, String body, Date publish_at) {
        this.title = title;
        this.slug = slug;
        this.body = body;
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

    public Date getPublish_at() {
        return publish_at;
    }

    public void setPublish_at(Date publish_at) {
        this.publish_at = publish_at;
    }
}
