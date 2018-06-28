package com.example.a1505197.highradiusfeedy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class EmployeeInfo extends AppCompatActivity {
    Pager pager;
    private ViewPager mViewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);
        mViewPager=findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Recieved"));
        tabLayout.addTab(tabLayout.newTab().setText("Given"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        pager=new Pager(getSupportFragmentManager(),2);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(pager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(pager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



    }
}
