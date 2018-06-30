package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeInfo extends AppCompatActivity {
    Pager pager;
    private ViewPager mViewPager;
    ImageView editEmployeeInfo;
    CircleImageView userImage;
    Toolbar toolbar;
    String department,designation,image_url,name;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);
        mViewPager=findViewById(R.id.viewpager);
        userImage=findViewById(R.id.userInfoImage);
        username=findViewById(R.id.user_name);
        editEmployeeInfo=findViewById(R.id.edit_user_profile);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Recieved"));
        tabLayout.addTab(tabLayout.newTab().setText("Given"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        UserSessiondata sessiondata=new UserSessiondata();
        department=sessiondata.getDepartment();
        designation=sessiondata.getDesignation();
        image_url= sessiondata.getImage_url();
        name=sessiondata.getName();
        Picasso.with(EmployeeInfo.this)
                .load(image_url)
                .fit()
                .centerCrop()
                .into(userImage);
        username.setText(name);


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
//