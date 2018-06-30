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

import java.util.List;

/**
 * Created by 1505197 on 6/29/2018.
 */

public class EmployessCardAdapter extends ArrayAdapter<EmployessCards>
{
    Context context;
    List<EmployessCards> data;
    public EmployessCardAdapter(@NonNull Context context, int resource, @NonNull List<EmployessCards> objects) {
        super(context, resource, objects);
        this.context=context;
        this.data=objects;
    }
    public View getView(int positions,View convertView,ViewGroup parent)
    {
        EmployessCards cards=getItem(positions);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.give_feedback,parent,false);

        }
        TextView employeedesignation=convertView.findViewById(R.id.card_desination);
        ImageView employeeImage=convertView.findViewById(R.id.review_card_image);
        TextView  employeecardname=convertView.findViewById(R.id.review_card_name);
        TextView employeecarddepartment=convertView.findViewById(R.id.review_card_department);
        ///set the different values recieved from firebase
        employeecarddepartment.setText(cards.getDepartment());
        employeedesignation.setText(cards.getDesignation());
        Picasso.with(context)
                .load(data.get(positions).image_url)
                .fit()
                .centerCrop()
                .into(employeeImage);
        employeecardname.setText(cards.getName());


        return convertView;
    }

}
//