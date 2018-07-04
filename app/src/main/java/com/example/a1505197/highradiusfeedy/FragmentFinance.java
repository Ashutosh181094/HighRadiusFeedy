package com.example.a1505197.highradiusfeedy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by 1505197 on 7/2/2018.
 */

public class FragmentFinance extends Fragment
{
    RecyclerView recyclerViewFinance;
    DatabaseReference databaseReferenceFinance;
    ArrayList<EmployessCards> al;
    Long levelemp;
    EmployeeInAPerticularDepartmentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_finance,container,false);
        al=new ArrayList<>();
        UserSessiondata userSessiondata=new UserSessiondata();
        levelemp=userSessiondata.getLevel();

        recyclerViewFinance=view.findViewById(R.id.userSideRecyclerView);
        databaseReferenceFinance= FirebaseDatabase.getInstance().getReference("userinfo").child("Finance");
        databaseReferenceFinance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        EmployessCards employessCards=dataSnapshot1.getValue(EmployessCards.class);
                        if (employessCards.level==levelemp+1)
                        {
                            al.add(employessCards);
                        }

                    }
                }
                if (getActivity()!=null)
                {
                    adapter=new EmployeeInAPerticularDepartmentAdapter(getActivity(),al);
                    recyclerViewFinance.setAdapter(adapter);
                    recyclerViewFinance.setHasFixedSize(true);
                    recyclerViewFinance.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
