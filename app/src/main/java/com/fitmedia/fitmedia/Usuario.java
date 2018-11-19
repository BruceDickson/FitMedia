package com.fitmedia.fitmedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Usuario {

    private String fullname;
    private Integer type;
    private long date;
    private Map<String, Integer> following;

    public Usuario() {
    }


    public Usuario(String fullname, Integer type, long date, Map<String, Integer> following) {
        this.fullname = fullname;
        this.type = type;
        this.date = date;
        this.following = following;
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

    public Map<String, Integer>  getFollowing() { return following; }
}
