package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AboutEmployeeActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    RecyclerView recyclerView;
    FeedBackRecievedAdapter feedBackRecievedAdapter;
    DatabaseReference databaseReference;
    ArrayList<Feedbacks> feedbackRecieved;
    int positive=0;
    int negative=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_employee);
        imageView=findViewById(R.id.userInfoImage);
        textView=findViewById(R.id.user_name);
        recyclerView=findViewById(R.id.userSideRecyclerView);
        feedbackRecieved=new ArrayList<>();

        Intent intent=getIntent();
        String department=intent.getStringExtra("sendDepartment");
        String name=intent.getStringExtra("sendName");
        final String email=intent.getStringExtra("sendEmail");
        String  designation=intent.getStringExtra("designation");
        String image_url=intent.getStringExtra("image");
        Picasso.with(AboutEmployeeActivity.this)
                .load(image_url)
                .fit()
                .centerCrop()
                .into(imageView);
        textView.setText(name);
        databaseReference= FirebaseDatabase.getInstance().getReference("feedback").child(""+department);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Feedbacks feedbacks = dataSnapshot1.getValue(Feedbacks.class);
                        if (feedbacks.getGiven_to().equals(email)) {

                            String timestamp = getTimeStampDifference(feedbacks.getDate());

                            if (timestamp.equals("0")) {
                                feedbacks.date = timestamp + "DAYS AGO";
                            } else {
                                feedbacks.date = "TODAY";
                            }
                            feedbackRecieved.add(feedbacks);
                            if (feedbacks.type.equals("Positive")) {
                                positive++;
                            } else {
                                negative++;
                            }
                        }

                    }
                }
                if (getApplicationContext()!=null) {

                    feedBackRecievedAdapter = new FeedBackRecievedAdapter(getApplicationContext(), feedbackRecieved);
                    recyclerView.setAdapter(feedBackRecievedAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    feedBackRecievedAdapter.notifyDataSetChanged();
                }
                List<PieEntry> p=new ArrayList<>();

                p.add(new PieEntry(positive,"Positive"));
                p.add(new PieEntry(negative,"Negative"));


                PieDataSet pieDataSet=new PieDataSet(p,"Feedback");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData pieData=new PieData(pieDataSet);
                PieChart chart=findViewById(R.id.ratingChart);
                chart.setCenterText("Feedback");
                chart.setData(pieData);
                chart.animateY(1000);
                chart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public String getTimeStampDifference(String timeStampfeedback)
    {
        String difference="";
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("ISO"));
        Date today= calendar.getTime();
        simpleDateFormat.format(today);
        Date timestamp;
        try
        {
            timestamp=simpleDateFormat.parse(timeStampfeedback);
            difference=String.valueOf(Math.round(((today.getTime()-timestamp.getTime())/1000/60/60/24)));
        } catch (ParseException e) {
            e.printStackTrace();
            difference="";
        }
        return difference;
    }
}
