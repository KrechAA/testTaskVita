package com.krech.vita.domain.db;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "author", nullable = false)
    private long author;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date")
    private Date date;

    public Task() {
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task task = (Task)super.clone();
        task.id = this.id;
        task.author = this.author;
        task.status = this.status;
        task.text = this.text;
        task.date = this.date;

        return task;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
