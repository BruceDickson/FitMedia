package com.fitmedia.fitmedia;

public class Post {

    private String content;
    private String id_user;
    private Long date;

    public Post(){

    }

    public Post(String content, String id_user, Long date) {
        this.content = content;
        this.id_user = id_user;
        this.date = date;
    }

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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "content='" + content + '\'' +
                ", id_user='" + id_user + '\'' +
                ", date=" + date +
                '}';
    }
}
