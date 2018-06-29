package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 6/29/2018.
 */

public class EmployessCards
{
    String department;
    String designation;
    String image_url;
    String name;

    public EmployessCards() {
    }

    public EmployessCards(String department, String designation, String image_url, String name) {
        this.department = department;
        this.designation = designation;
        this.image_url = image_url;
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
}
