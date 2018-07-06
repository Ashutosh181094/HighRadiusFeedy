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
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.bwsmiley_happy) // int top drawable
                .setSummary("This is summary")
                .build());
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#018EFE")) // int background color
                .setDrawable(R.drawable.high_radius_logo) // int top drawable
                .setSummary("This is summary")
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
