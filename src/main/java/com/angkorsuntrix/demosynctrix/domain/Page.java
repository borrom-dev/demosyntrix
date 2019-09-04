package com.angkorsuntrix.demosynctrix.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    private String content;
    private int parentId;
    private int ltf;
    private int depth;
    private int hidden;
    private String template;
    private String name;

    public Page() {
    }

    public Page(String url, String content, int parentId, int ltf, int depth, int hidden, String template, String name) {
        this.url = url;
        this.content = content;
        this.parentId = parentId;
        this.ltf = ltf;
        this.depth = depth;
        this.hidden = hidden;
        this.template = template;
        this.name = name;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLtf() {
        return ltf;
    }

    public void setLtf(int ltf) {
        this.ltf = ltf;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
