package com.example.a1505197.highradiusfeedy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1505197 on 7/5/2018.
 */

public class SecurityCardAdapter extends ArrayAdapter<FoodObject>
{
    Context context;
    List<FoodObject> data;
    private ArrayList<FoodObject> arrayList;

    public SecurityCardAdapter(@NonNull Context context, int resource, @NonNull List<FoodObject> objects) {
        super(context, resource, objects);
        this.context=context;
        this.data=objects;
        arrayList=new ArrayList<>();
        this.arrayList.addAll(data);
    }
    public View getView(final int positions, View convertView, ViewGroup parent)
    {
        FoodObject cards=getItem(positions);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.give_security_feedback,parent,false);

        }
        ImageView employeeImage=convertView.findViewById(R.id.review_card_image);
        TextView employeecardname=convertView.findViewById(R.id.review_card_name);
        TextView employeecarddepartment=convertView.findViewById(R.id.review_card_department);
        employeecarddepartment.setText("Security");
        Picasso.with(context)
                .load(data.get(positions).image_url)
                .fit()
                .centerCrop()
                .into(employeeImage);


        return convertView;
    }

}
