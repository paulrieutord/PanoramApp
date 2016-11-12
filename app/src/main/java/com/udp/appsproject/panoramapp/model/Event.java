package com.udp.appsproject.panoramapp.model;

public class Event {
    private String title;
    private long dateTime;
    private long createdAt;
    private String place;
    private String comment;
    private String createdBy;
    private String categorie;

    public Event (){
    }

    public Event (String title, long dateTime, long createdAt, String place, String comment, String createdBy, String categorie) {
        this.title = title;
        this.dateTime = dateTime;
        this.createdAt = createdAt;
        this.place = place;
        this.comment = comment;
        this.createdBy = createdBy;
        this.categorie = categorie;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
