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
import android.widget.Toast;

/**
 * Created by 1505197 on 6/29/2018.
 */

public class FragmentSubmit extends Fragment
{
    CardView cardView_manager,cardView_hr,cardView_admin,cardView_food,cardView_security,cardView_etc;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_submit,container,false);
        cardView_manager=view.findViewById(R.id.card_manager);
        cardView_admin=view.findViewById(R.id.card_admin);
        cardView_hr=view.findViewById(R.id.card_hr);
       // cardView_etc=view.findViewById(R.id.card_etc);
        UserSessiondata userSessiondata=new UserSessiondata();
        String department=userSessiondata.getDepartment();
        Toast.makeText(getActivity(), ""+department, Toast.LENGTH_SHORT).show();
        if(department.equals("Finance"))
        {
            cardView_hr.setVisibility(View.INVISIBLE);
            cardView_admin.setVisibility(View.INVISIBLE);
        }
        else
            if (department.equals("Admin"))
            {
                cardView_manager.setVisibility(View.INVISIBLE);
                cardView_hr.setVisibility(View.INVISIBLE);
            }
            else
            {
                cardView_manager.setVisibility(View.INVISIBLE);
                cardView_admin.setVisibility(View.INVISIBLE);
            }
        cardView_security=view.findViewById(R.id.card_security);
        cardView_food=view.findViewById(R.id.card_food);
        cardView_hr=view.findViewById(R.id.card_hr);
        cardView_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Employess.class);
                intent.putExtra("tag","finance");
                startActivity(intent);
            }
        });
        cardView_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getContext(),Employess.class);
                intent.putExtra("tag","admin");
                startActivity(intent);
            }
        });
        cardView_hr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Employess.class);
                intent.putExtra("tag","hr");
                startActivity(intent);
            }
        });
        cardView_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Employess.class);
                intent.putExtra("tag","food");
                startActivity(intent);
            }
        });
        cardView_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Employess.class);
                intent.putExtra("tag","security");
                startActivity(intent);
            }
        });
//        cardView_etc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),Employess.class);
//                intent.putExtra("tag","etc");
//                startActivity(intent);
//            }
//        });
        return view;
    }
}
