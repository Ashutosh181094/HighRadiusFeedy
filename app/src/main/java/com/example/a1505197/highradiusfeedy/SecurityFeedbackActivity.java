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

public class SecurityFeedbackActivity extends AppCompatActivity
{
    SecurityFeedbackActivityAdapter adapter;
    RecyclerView recyclerView;
    DatabaseReference Securityfeedbacks;
    ArrayList<FeedbackSecurityObject> al;
    ImageView logout;
    ImageView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_feedback);
        al=new ArrayList<>();
        logout=findViewById(R.id.logout_button);
        userInfo=findViewById(R.id.userImage);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecurityFeedbackActivity.this,EmployeeInfo.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.userSideRecyclerView);
        Securityfeedbacks= FirebaseDatabase.getInstance().getReference("feedback").child("Security");
        Securityfeedbacks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        FeedbackSecurityObject feedbackSecurityObject=dataSnapshot1.getValue(FeedbackSecurityObject.class);
                        al.add(feedbackSecurityObject);

                    }
                }
                if (getApplicationContext()!=null)
                {

                    adapter=new SecurityFeedbackActivityAdapter(SecurityFeedbackActivity.this,al);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SecurityFeedbackActivity.this));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
