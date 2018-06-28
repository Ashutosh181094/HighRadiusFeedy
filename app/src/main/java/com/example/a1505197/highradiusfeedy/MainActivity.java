package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView userImage;
    ViewPager viewPager=null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }


}
class Mypager extends FragmentPagerAdapter
{

    public Mypager(FragmentManager fm) {
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
            return "Submit";
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
