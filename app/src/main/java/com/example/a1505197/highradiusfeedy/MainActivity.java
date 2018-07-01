package com.example.a1505197.highradiusfeedy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView userImage;
    ViewPager viewPager=null;
    int indexoffirst;
    DatabaseReference userdata;
    private ArrayList<EmployessCards> al;
    String designation;
    DatabaseReference feedbackRecieved;


    //String designation;
    String key;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        al = new ArrayList<EmployessCards>();
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        indexoffirst=email.indexOf('@');
        key=email.substring(0,indexoffirst);
        userImage=findViewById(R.id.userImage);
        viewPager=findViewById(R.id.mainActivity_ViewPager);
        viewPager.setAdapter(new Mypager(getSupportFragmentManager()));

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(MainActivity.this,EmployeeInfo.class);
                startActivity(intent);
            }
        });
        userdata = FirebaseDatabase.getInstance().getReference("registeredEmployees").child(""+key);
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value[]=new String[5];
                int i=0;
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        value[i]=(String)dataSnapshot1.getValue();
                        i++;


                    }
                }
                Picasso.with(MainActivity.this)
                        .load(value[3])
                        .fit()
                        .centerCrop()
                        .into(userImage);
                UserSessiondata sessiondata=new UserSessiondata();
                sessiondata.setDepartment(value[0]);
                sessiondata.setDesignation(value[1]);
                sessiondata.setImage_url(value[3]);
                sessiondata.setName(value[4]);




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        feedbackRecieved=FirebaseDatabase.getInstance().getReference("feedback").child(""+key);
        feedbackRecieved.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
              sendNotification();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public void sendNotification()
    {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

        //Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(MainActivity.this,EmployeeInfo.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.koleeg_green);
        mBuilder.setContentTitle("New Feedback");
        mBuilder.setContentText("A new Feedback given to you");
        mBuilder.setColor(Color.GREEN);

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }


}
class Mypager extends FragmentPagerAdapter
{

    public Mypager(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment=null;
       if(position==0)
       {
           fragment=new FragmentSubmit();
       }
       else
           if(position==1)
           {
               fragment=new FragmentHR();
           }
           else
               if(position==2)
               {
                fragment=new FragmentFood();
               }
               else
                   if(position==3)
                   {
                       fragment=new FragmentAdmin();
                   }
                   else
                       if(position==4)
                       {
                           fragment=new FragmentSecurity();
                       }
                       else
                           if(position==5)
                           {
                              fragment=new FragmentEtc();
                           }
                           return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "SUBMIT";
            else
        if(position==1)
            return "HR";
            else
        if(position==2)
            return "FOOD";
            else
        if(position==3)
            return "ADMIN";
            else
        if(position==4)
            return "SECURITY";
            else
        if(position==5)
            return "ETC";

            return null;



    }
}
