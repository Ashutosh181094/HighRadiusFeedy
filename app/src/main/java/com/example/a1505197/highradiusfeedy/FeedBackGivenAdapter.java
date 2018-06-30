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
 * Created by 1505197 on 6/30/2018.
 */

public class FeedBackGivenAdapter extends RecyclerView.Adapter<FeedBackGivenAdapter.VendorViewHolder> {
    Context context;
    LayoutInflater inflater;
    List<Feedbacks> data;
    public FeedBackGivenAdapter(Context context, List<Feedbacks> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public FeedBackGivenAdapter.VendorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_card_given, parent, false);
        FeedBackGivenAdapter.VendorViewHolder holder = new FeedBackGivenAdapter.VendorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedBackGivenAdapter.VendorViewHolder holder, int position) {


        holder.name.setText(data.get(position).name);
        holder.date.setText(data.get(position).date);
        holder.description.setText(data.get(position).feedback);


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
        TextView date;
        TextView description;
        CircleImageView circleImageView;

        public VendorViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name);
            date=itemView.findViewById(R.id.date_given);
            description=itemView.findViewById(R.id.description_given);
            circleImageView=itemView.findViewById(R.id.profileimage);


        }
    }
}
