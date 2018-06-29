package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class EmployeeInfo extends AppCompatActivity {
    Pager pager;
    private ViewPager mViewPager;
    ImageView editEmployeeInfo;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);
        mViewPager=findViewById(R.id.viewpager);
        editEmployeeInfo=findViewById(R.id.edit_user_profile);
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

        editEmployeeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EmployeeInfo.this,EditEmployeeDetails.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
