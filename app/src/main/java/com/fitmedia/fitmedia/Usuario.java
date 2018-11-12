package com.fitmedia.fitmedia;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String fullname;
    private Integer type;
    private long date;
    private List<String> following = new ArrayList<String>();

    public Usuario() {
    }


    public Usuario(String fullname, Integer type, long date) {
        this.fullname = fullname;
        this.type = type;
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }
}
