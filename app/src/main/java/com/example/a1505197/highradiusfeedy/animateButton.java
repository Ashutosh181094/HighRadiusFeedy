package com.example.a1505197.highradiusfeedy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.like.LikeButton;
import com.like.OnLikeListener;

public class animateButton extends AppCompatActivity {

    LikeButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ani);
        likeButton=findViewById(R.id.star_button);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });


    }

}
