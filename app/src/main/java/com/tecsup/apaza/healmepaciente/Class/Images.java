package com.tecsup.apaza.healmepaciente.Class;

import java.util.Date;

public class Images {

    private Integer id;
    private String name;
    private String url;
    private Date date;
    private Integer commentable_id;
    private String commentable_type;
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCommentable_id() {
        return commentable_id;
    }

    public void setCommentable_id(Integer commentable_id) {
        this.commentable_id = commentable_id;
    }

    public String getCommentable_type() {
        return commentable_type;
    }

    public void setCommentable_type(String commentable_type) {
        this.commentable_type = commentable_type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
