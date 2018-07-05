package com.example.a1505197.highradiusfeedy;

import android.os.Bundle;
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
 * Created by 1505197 on 6/29/2018.
 */

public class FragmentFood extends Fragment
{
    ArrayList<FoodObject> al;
    FragmentFoodAdapter fragmentFoodAdapter;
    DatabaseReference firebaseDatabasefragmentfood;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food,container,false);
        al=new ArrayList<>();
        recyclerView=view.findViewById(R.id.userSideRecyclerView);
        firebaseDatabasefragmentfood=FirebaseDatabase.getInstance().getReference("food");
        firebaseDatabasefragmentfood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
             if (dataSnapshot.exists())
             {
                 for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                 {
                     FoodObject foodObject=dataSnapshot1.getValue(FoodObject.class);
                     al.add(foodObject);
                 }
             }
                if (getActivity()!=null)
                {

                    fragmentFoodAdapter=new FragmentFoodAdapter(getActivity(),al);
                    recyclerView.setAdapter(fragmentFoodAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    fragmentFoodAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
