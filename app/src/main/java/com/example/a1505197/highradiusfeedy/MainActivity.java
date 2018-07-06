package com.example.a1505197.highradiusfeedy;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseUser user;
    public PagerTabStrip pagerTabStrip;
 FirebaseAuth mAuth;
    Context context;
    ProgressDialog progressDialog;
    DatabaseReference userdata;
    private ArrayList<RegisteredEmployeesData> al;



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
        pagerTabStrip=findViewById(R.id.title);

//

        pagerTabStrip.setTextColor(getResources().getColor(R.color.White));
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.grey1));
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.getOwnerActivity();
        progressDialog.setMessage("Please wait while we fetch your data");
        progressDialog.show();
//



        logoutImage=findViewById(R.id.logout_button);
        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calldialog();


                //finish();

            }

            private void calldialog() {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(MainActivity.this,Login.class);
                        startActivity(intent);
                        //if user pressed "yes", then he is allowed to exit from application
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


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

    @Override
    protected void onStart() {
        super.onStart();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            FirebaseAuth.getInstance().signOut();
        }
        else
        {

            //////////////////////////////////////


            Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getBoolean("isFirstRun", true);
           // Toast.makeText(this, ""+isFirstRun.toString(), Toast.LENGTH_SHORT).show();

            if (isFirstRun) {
                //show start activity

                Intent intent = new Intent(MainActivity.this,OnBoarding.class);

                startActivity(intent);
                //finish();

                //startActivity(new Intent(this, OnBoarding.class));
                Toast.makeText(getApplicationContext(), "First Run", Toast.LENGTH_LONG)
                        .show();
                //finish();
            }
            else {
               // startActivity(new Intent(this, Login.class));
               // finish();

            }


            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();







            /////////////////////////////////////////
        }
    }
    protected void onDestroy () {
        super.onDestroy();
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        progressDialog = null;
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
