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

    public Feedbacks()
    {
    }

    public Feedbacks(String feedback, String name, String image_url, String date, String type) {
        this.feedback = feedback;
        this.name = name;
        this.image_url = image_url;
        this.date = date;
        this.type = type;
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
}
