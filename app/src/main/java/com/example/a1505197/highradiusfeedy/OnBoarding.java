package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class OnBoarding extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(new Step.Builder().setTitle("Welcome to Koleeg")
                .setContent("We help you share your thoughts about your colleagues")
                .setBackgroundColor(Color.parseColor("#2ecc71")) // int background color
                .setDrawable(R.drawable.koleeg_white) // int top drawable
                .setSummary("This is summary")
                .build());
        addFragment(new Step.Builder().setTitle("Feedback Made Easy")
                .setContent("We at Koleeg, beleive in Minimalistic design pattern. Hence, the effort to select various features of person is removed. Swipe LEFT for positive review and RIGHT for negative review")
                .setBackgroundColor(Color.parseColor("#018EFE")) // int background color
                .setDrawable(R.drawable.swipeinstructions) // int top drawable
                .setSummary("Happy Swiping")
                .build());
        addFragment(new Step.Builder().setTitle("Be anonymous")
                .setContent("Koleeg helps you submit anonymous feedback")
                .setBackgroundColor(Color.parseColor("#34495e")) // int background color
                .setDrawable(R.drawable.anonymous) // int top drawable
                .setSummary("")
                .build());

        addFragment(new Step.Builder().setTitle("Track your goals")
                .setContent("With our analytics, track your growth in the company. Remember, a leader is the one who is liked and respected by subordinates")
                .setBackgroundColor(Color.parseColor("#1FAFB4")) // int background color
                .setDrawable(R.drawable.analytics) // int top drawable
                .setSummary("")
                .build());





    }
    @Override
    public void finishTutorial() {
        // Your implementation
        Intent intent=new Intent(OnBoarding.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
