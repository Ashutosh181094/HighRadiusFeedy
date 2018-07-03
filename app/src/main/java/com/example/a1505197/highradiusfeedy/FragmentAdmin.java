package com.example.a1505197.highradiusfeedy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * Created by 1505197 on 6/29/2018.
 */

public class FragmentAdmin extends Fragment
{
    RecyclerView recyclerViewAdmin;
    DatabaseReference databaseReferenceAdmin;
    ArrayList<EmployessCards> al;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_admin,container,false);
        al=new ArrayList<>();
        recyclerViewAdmin=view.findViewById(R.id.userSideRecyclerView);
        databaseReferenceAdmin= FirebaseDatabase.getInstance().getReference("userinfo").child("admin");
        databaseReferenceAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        EmployessCards employessCards=dataSnapshot1.getValue(EmployessCards.class);
                        al.add(employessCards);

                    }
                }
                if (getActivity()!=null)
                {
//                    adapter = new EmployeeInAPerticularDepartmentAdapter(getActivity(), al);
//                    recyclerViewAdmin.setAdapter(adapter);
//                    recyclerViewAdmin.setHasFixedSize(true);
//                    recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
