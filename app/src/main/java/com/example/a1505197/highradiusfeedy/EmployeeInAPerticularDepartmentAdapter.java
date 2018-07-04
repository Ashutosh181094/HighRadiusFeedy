package com.example.a1505197.highradiusfeedy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505197 on 7/1/2018.
 */


public class EmployeeInAPerticularDepartmentAdapter extends RecyclerView.Adapter<EmployeeInAPerticularDepartmentAdapter.VendorViewHolder>
        {
    Context context;
    LayoutInflater inflater;
    List<EmployessCards> data;
    public EmployeeInAPerticularDepartmentAdapter(Context context, List<EmployessCards> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public EmployeeInAPerticularDepartmentAdapter.VendorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_others_feedback_card, parent, false);
        EmployeeInAPerticularDepartmentAdapter.VendorViewHolder holder = new EmployeeInAPerticularDepartmentAdapter.VendorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EmployeeInAPerticularDepartmentAdapter.VendorViewHolder holder, int position) {


        holder.name.setText(data.get(position).name);
        holder.department.setText(data.get(position).department);
        holder.happysmiley.setText(data.get(position).positive.toString());
        holder.sadsmiley.setText(data.get(position).negative.toString());


        Picasso.with(context)
                .load(data.get(position).image_url)
                .fit()
                .centerCrop()
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VendorViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView department;
        TextView happysmiley;
        TextView neutralsmiley;
        TextView sadsmiley;
        CircleImageView circleImageView;

        public VendorViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.view_feedback_name_tv);
            department=itemView.findViewById(R.id.view_feedback_department_tv);
            happysmiley=itemView.findViewById(R.id.numberHappy);
            //neutralsmiley=itemView.findViewById(R.id.numberNeutral);
            sadsmiley=itemView.findViewById(R.id.numberSad);
            circleImageView=itemView.findViewById(R.id.view_feedback_image_view);


        }
    }
}
