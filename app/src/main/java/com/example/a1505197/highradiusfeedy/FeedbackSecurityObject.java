package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 7/5/2018.
 */

public class FeedbackSecurityObject
{
    String date;
    String feedback;
    String given_by;
    String image_url;
    String name;
    String type;
    String question;

    public FeedbackSecurityObject() {
    }

    public FeedbackSecurityObject(String date, String feedback, String given_by, String image_url, String name, String type, String question) {
        this.date = date;
        this.feedback = feedback;
        this.given_by = given_by;
        this.image_url = image_url;
        this.name = name;
        this.type = type;
        this.question = question;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGiven_by() {
        return given_by;
    }

    public void setGiven_by(String given_by) {
        this.given_by = given_by;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
