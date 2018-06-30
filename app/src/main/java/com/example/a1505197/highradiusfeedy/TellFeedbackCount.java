package com.example.a1505197.highradiusfeedy;

/**
 * Created by 1505197 on 6/30/2018.
 */

public class TellFeedbackCount
{
    static  int positive;
    static int negative;

    public static int getPositive() {
        return positive;
    }

    public static void setPositive(int positive) {
        TellFeedbackCount.positive = positive;
    }

    public static int getNegative() {
        return negative;
    }

    public static void setNegative(int negative) {
        TellFeedbackCount.negative = negative;
    }
}
