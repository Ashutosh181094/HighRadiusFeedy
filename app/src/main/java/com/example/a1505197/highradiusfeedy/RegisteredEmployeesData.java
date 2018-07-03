package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 7/3/2018.
 */

public class RegisteredEmployeesData
{
    String department;
    String designation;
    String email;
    String image_url;
    Long level;
    String name;

    public RegisteredEmployeesData() {
    }

    public RegisteredEmployeesData(String department, String designation, String email, String image_url, Long level, String name) {
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.image_url = image_url;
        this.level = level;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
