package com.krech.vita.domain.rest.request;

import java.util.Date;

public class TaskCreateRequest {


    private long author;
    private String status;
    private String text;
    private Date date;

    public TaskCreateRequest() {
    }

    public TaskCreateRequest(int author, String status, String text, Date date) {
        this.author = author;
        this.status = status;
        this.text = text;
        this.date = date;
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
