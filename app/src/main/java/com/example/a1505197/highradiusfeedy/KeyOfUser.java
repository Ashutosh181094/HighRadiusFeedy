package com.example.a1505197.highradiusfeedy;

public class KeyOfUser {


    public String getUserKey(String key)
    {
        return (key.replaceAll("[-+.^:,]",""));
    }
}
