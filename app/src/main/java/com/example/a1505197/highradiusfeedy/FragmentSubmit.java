package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by 1505197 on 6/29/2018.
 */

public class FragmentSubmit extends Fragment
{
    ImageView managericon;
    CardView cardView_manager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_submit,container,false);
        managericon=view.findViewById(R.id.manager);
        cardView_manager=view.findViewById(R.id.card_manager);
        managericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Employess.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
