package com.krech.vita.domain.rest.response;


import java.util.Date;


public class TaskResponse {

    private long id;
    private long author;
    private String status;
    private String text;
    private Date date;

    public TaskResponse(long id, long author, String status, String text, Date date) {
        this.id = id;
        this.author = author;
        this.status = status;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
