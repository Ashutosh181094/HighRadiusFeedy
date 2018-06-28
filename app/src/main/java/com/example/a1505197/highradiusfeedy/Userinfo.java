package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 6/28/2018.
 */

public class Userinfo
{
    String name;
    String image_url;
    String designation;

    public Userinfo() {
    }

    public Userinfo(String name, String image_url, String designation) {
        this.name = name;
        this.image_url = image_url;
        this.designation = designation;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
