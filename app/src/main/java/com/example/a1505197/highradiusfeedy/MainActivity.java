package com.example.a1505197.highradiusfeedy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.Toast;

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
    private ArrayList<RegisteredEmployeesData> al;
    String designation;
    DatabaseReference feedbackRecieved;
    int i=0;
    int j=0;

    ImageView logoutImage;
    //String designation;
    String key;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        al = new ArrayList<>();
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        indexoffirst=email.indexOf('@');
        key=email.substring(0,indexoffirst);
        userImage=findViewById(R.id.userImage);
        viewPager=findViewById(R.id.mainActivity_ViewPager);



        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait while we fetch your data");
        progressDialog.show();


        logoutImage=findViewById(R.id.logout_button);
        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();

            }
        });






        userdata = FirebaseDatabase.getInstance().getReference("registeredemployees");
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i=0;
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                       RegisteredEmployeesData registeredEmployeesData=dataSnapshot1.getValue(RegisteredEmployeesData.class);
                      if(registeredEmployeesData.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                      {
                          al.add(registeredEmployeesData);
                      }
                    }

                }

                UserSessiondata sessiondata=new UserSessiondata();
                sessiondata.setDepartment(al.get(0).department);
                sessiondata.setDesignation(al.get(0).designation);
                sessiondata.setImage_url(al.get(0).image_url);
                sessiondata.setLevel(al.get(0).level);
                sessiondata.setName(al.get(0).name);
                Picasso.with(MainActivity.this)
                        .load(al.get(0).getImage_url())
                        .fit()
                        .centerCrop()
                        .into(userImage);
              progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+sessiondata.getDepartment(), Toast.LENGTH_SHORT).show();

              viewPager.setAdapter(new Mypager(getSupportFragmentManager()));




            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });




        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(MainActivity.this,EmployeeInfo.class);
                startActivity(intent);
            }
        });

        feedbackRecieved= FirebaseDatabase.getInstance().getReference("feedback").child(""+key);
        feedbackRecieved.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
               j++;
               if(j==2)
               {


                   sendNotification();
               }



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
    UserSessiondata userSessiondata;
    String department;
    private static final String TAG = "Mypager";
    public Mypager(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment=null;
        userSessiondata=new UserSessiondata();
       department=userSessiondata.getDepartment();
       if(position==0)
       {
           fragment=new FragmentSubmit();
       }
       else
               if(position==1)
               {
                fragment=new FragmentFood();
               }
               else
                   if(position==2)
                   {
                       fragment=new FragmentSecurity();
                   }
                   else
        if(position==3)
        {
            if(department.equals("Finance"))
            {
                fragment=new FragmentFinance();
            }
            else
            if(department.equals("Hr"))
            {
                fragment=new FragmentHR();
            }
            else
            if(department.equals("Admin"))
            {
                fragment=new FragmentAdmin();
            }

        }

                           return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        userSessiondata=new UserSessiondata();
        department=userSessiondata.getDepartment();
        if(position==0)
            return "SUBMIT";

            else
        if(position==1)
            return "FOOD";
            else
        if(position==2)
            return "SECURITY";
        else
        if(position==3)

            if(department.equals("Finance"))
            {
                return "FINANCE";
            }
            else
            if(department.equals("Hr"))
            {
                return "HR";
            }
            else
            if(department.equals("Admin"))
            {
                return "ADMIN";
            }


            return null;



    }

}
