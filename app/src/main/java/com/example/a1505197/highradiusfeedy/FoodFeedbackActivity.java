package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodFeedbackActivity extends AppCompatActivity
{
    FoodFeedbackActivityAdapter adapter;
    RecyclerView recyclerView;
    DatabaseReference Foodfeedbacks;
    ArrayList<FeedbackFoodObject> al;
    ImageView logout;
    ImageView userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feedback);
        logout=findViewById(R.id.logout_button);
        userInfo=findViewById(R.id.userImage);
        Intent intent=getIntent();
        final String question=intent.getStringExtra("question");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FoodFeedbackActivity.this,EmployeeInfo.class);
                startActivity(intent);
            }
        });
        al=new ArrayList<>();
        recyclerView=findViewById(R.id.userSideRecyclerView);
        Foodfeedbacks= FirebaseDatabase.getInstance().getReference("feedback").child("Food");
        Foodfeedbacks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {

                        FeedbackFoodObject feedbackfoodObject=dataSnapshot1.getValue(FeedbackFoodObject.class);
                        if(question.equals(feedbackfoodObject.getQuestion()))
                        {
                            al.add(feedbackfoodObject);
                        }

                    }
                }
                if (getApplicationContext()!=null)
                {

                    adapter=new FoodFeedbackActivityAdapter(FoodFeedbackActivity.this,al);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FoodFeedbackActivity.this));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
