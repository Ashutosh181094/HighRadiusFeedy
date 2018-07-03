package com.example.a1505197.highradiusfeedy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 1505197 on 6/29/2018.
 */

public class FeedbackRecievedFragment extends Fragment
{
    DatabaseReference Feedbackrecieved;
    ArrayList<Feedbacks> feedbackRecieved;
    FeedBackRecievedAdapter feedBackRecievedAdapter;
    RecyclerView recyclerView;
    int indexoffirst;
    int positive=0,negative=0;
    String key;
    View view2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_feedback_recieved,container,false);
        view2=view;
        recyclerView=view.findViewById(R.id.userSideRecyclerView);
        feedbackRecieved=new ArrayList<>();
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        indexoffirst=email.indexOf('@');
        key=email.substring(0,indexoffirst);
        Feedbackrecieved= FirebaseDatabase.getInstance().getReference("feedback").child(""+key);
        Feedbackrecieved.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        Feedbacks feedbacks=dataSnapshot1.getValue(Feedbacks.class);
                        String timestamp=getTimeStampDifference(feedbacks.getDate());
                        if(timestamp.equals("0"))
                        {
                            feedbacks.date=timestamp+"DAYS AGO";
                        }
                        else
                        {
                            feedbacks.date="TODAY";
                        }
                        feedbackRecieved.add(feedbacks);
                        if(feedbacks.type.equals("Positive"))
                        {
                            positive++;
                        }
                        else
                        {
                         negative++;
                        }
                    }
                }
                if (getActivity()!=null) {

                    feedBackRecievedAdapter = new FeedBackRecievedAdapter(getActivity(), feedbackRecieved);
                    recyclerView.setAdapter(feedBackRecievedAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    feedBackRecievedAdapter.notifyDataSetChanged();
                }
                List<PieEntry> p=new ArrayList<>();

                p.add(new PieEntry(positive,"Positive"));
                p.add(new PieEntry(negative,"Negative"));


                PieDataSet pieDataSet=new PieDataSet(p,"Feedback");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData pieData=new PieData(pieDataSet);
                PieChart chart=view2.findViewById(R.id.ratingChart);
                chart.setCenterText("Feedback");
                chart.setData(pieData);
                chart.animateY(1000);
                chart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
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
