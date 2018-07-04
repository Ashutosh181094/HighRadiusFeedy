package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 6/30/2018.
 */

public class Feedbacks
{
    String feedback;
    String name;
    String image_url;
    String date;
    String type;
    String given_to;
    String given_by;
    Long level;

    public Feedbacks() {
    }

    public Feedbacks(String feedback, String name, String image_url, String date, String type, String given_to, String given_by, Long level) {
        this.feedback = feedback;
        this.name = name;
        this.image_url = image_url;
        this.date = date;
        this.type = type;
        this.given_to = given_to;
        this.given_by = given_by;
        this.level = level;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGiven_to() {
        return given_to;
    }

    public void setGiven_to(String given_to) {
        this.given_to = given_to;
    }

    public String getGiven_by() {
        return given_by;
    }

    public void setGiven_by(String given_by) {
        this.given_by = given_by;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }
}
