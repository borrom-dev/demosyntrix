package com.angkorsuntrix.demosynctrix.entity;

import com.angkorsuntrix.demosynctrix.entity.audit.DateAudit;
import com.angkorsuntrix.demosynctrix.payload.TopicRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "topics", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "url",
                "name"
        })
})
public class Topic extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(max = 200)
    private String url;
    @Column(columnDefinition = "text")
    private String content;
    @NotBlank
    @Size(max = 100)
    private String template;
    private long position;
    private boolean status;
    @NotBlank
    @Size(max = 200)
    private String name;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "topic",
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Article> articles;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "topic_meta", joinColumns = @JoinColumn(name = "id", nullable = false))
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> metaMap = new HashMap<>();

    public Topic() {
    }

    public Topic(TopicRequest request) {
        this.url = request.getUrl();
        this.content = request.getContent();
        this.template = request.getTemplate();
        this.name = request.getName();
        this.position = request.getPosition();
        this.metaMap = request.getMetaMap();
        this.status = request.isStatus();
    }

    public void update(TopicRequest request) {
        this.url = request.getUrl();
        this.template = request.getTemplate();
        this.name = request.getName();
        this.content = request.getContent();
        this.position = request.getPosition();
        this.status = request.isStatus();
        this.metaMap = request.getMetaMap();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Map<String, String> getMetaMap() {
        return metaMap;
    }

    public void setMetaMap(Map<String, String> metaMap) {
        this.metaMap = metaMap;
    }

}
