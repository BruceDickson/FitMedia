package com.fitmedia.fitmedia;

import java.io.Serializable;

public class Comment implements Serializable {

    private String content;
    private String id_user;
    private String name_user;
    private String id_post;
    private Long date;

    public Comment(String content, String id_user, String id_post, String name_user, Long date) {
        this.content = content;
        this.id_user = id_user;
        this.name_user = name_user;
        this.date = date;
        this.id_post = id_post;
    }

    public Comment(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }
}
