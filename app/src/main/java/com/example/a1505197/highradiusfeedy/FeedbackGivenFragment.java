package com.example.a1505197.highradiusfeedy;

import android.app.ProgressDialog;
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

public class FeedbackGivenFragment extends Fragment
{
    DatabaseReference Feedbackgiven;
    ArrayList<Feedbacks> feedbackGiven;
    FeedBackGivenAdapter feedBackGivenAdapter;
    RecyclerView recyclerView;
    View view2;
    int positive=0,negative=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_feedback_given,container,false);
        recyclerView=view.findViewById(R.id.userSideRecyclerView);
        feedbackGiven=new ArrayList<>();
        view2=view;
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait while we fetch your data");
        progressDialog.show();
       UserSessiondata userSessiondata=new UserSessiondata();
        Feedbackgiven= FirebaseDatabase.getInstance().getReference("feedback").child(userSessiondata.getDepartment());
        Feedbackgiven.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                        Feedbacks feedbacks = dataSnapshot1.getValue(Feedbacks.class);
                        String timestamp = getTimeStampDifference(feedbacks.getDate());

                        if (feedbacks.getGiven_by().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                        {
                            feedbackGiven.add(feedbacks);

                        }
                            if (timestamp.equals("0")) {
                                feedbacks.date = "TODAY";

                            } else {
                                feedbacks.date = timestamp + "DAYS AGO";
                            }
                        }


                    progressDialog.dismiss();
                }

                if (getActivity()!=null)
                {
                 feedBackGivenAdapter = new FeedBackGivenAdapter(getActivity(), feedbackGiven);
                 recyclerView.setAdapter(feedBackGivenAdapter);
                 recyclerView.setHasFixedSize(true);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                 feedBackGivenAdapter.notifyDataSetChanged();
                  }

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
