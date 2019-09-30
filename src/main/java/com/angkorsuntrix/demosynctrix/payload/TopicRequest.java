package com.angkorsuntrix.demosynctrix.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TopicRequest {
    @NotBlank
    @Size(max = 200)
    private String url;
    @NotBlank
    private String content;
    @NotBlank
    @Size(max = 100)
    private String template;
    private long position;
    @NotBlank
    @Size(max = 50)
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
