package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 7/5/2018.
 */

public class FoodObject
{
    String name;
    String image_url;
    Long negative;
    Long positive;
    String question;

    public FoodObject() {
    }

    public FoodObject(String name, String image_url, Long negative, Long positive, String question) {
        this.name = name;
        this.image_url = image_url;
        this.negative = negative;
        this.positive = positive;
        this.question = question;
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

    public Long getNegative() {
        return negative;
    }

    public void setNegative(Long negative) {
        this.negative = negative;
    }

    public Long getPositive() {
        return positive;
    }

    public void setPositive(Long positive) {
        this.positive = positive;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
