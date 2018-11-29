package com.fitmedia.fitmedia;

public class Usuario {

    private String fullname;
    private Integer type;
    private long date;

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
}
