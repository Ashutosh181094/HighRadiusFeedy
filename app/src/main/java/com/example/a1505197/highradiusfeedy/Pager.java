package com.example.a1505197.highradiusfeedy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kiit on 29/6/17.
 */

class Pager extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public Pager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position)
    {

        switch (position) {
            case 0:
                FeedbackRecievedFragment tab1=new FeedbackRecievedFragment();
                return tab1;
            case 1:
                 FeedbackGivenFragment tab2=new FeedbackGivenFragment();
                 return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return mNumOfTabs;
    }
}
////////////