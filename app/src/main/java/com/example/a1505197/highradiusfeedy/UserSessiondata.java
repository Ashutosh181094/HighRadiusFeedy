package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 6/29/2018.
 */

public class UserSessiondata
{
    static String department;
    static  String designation;
    static String email;
    static  String image_url;
    static  String name;
    static long level;

    public static String getDepartment() {
        return department;
    }

    public static void setDepartment(String department) {
        UserSessiondata.department = department;
    }

    public static String getDesignation() {
        return designation;
    }

    public static void setDesignation(String designation) {
        UserSessiondata.designation = designation;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserSessiondata.email = email;
    }

    public static String getImage_url() {
        return image_url;
    }

    public static void setImage_url(String image_url) {
        UserSessiondata.image_url = image_url;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserSessiondata.name = name;
    }

    public static long getLevel() {
        return level;
    }

    public static void setLevel(long level) {
        UserSessiondata.level = level;
    }
}
