package com.example.a1505197.highradiusfeedy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 1505197 on 6/29/2018.
 */

public class FragmentFood extends android.support.v4.app.Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food,container,false);
        return view;
    }
}
