package com.example.nsn11.dotcom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class RepairShopReputeProfileListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<RepairShopReputeProfile> arrayList;
    private int layout;
    LayerDrawable stars;

    public RepairShopReputeProfileListAdapter(Context context,int layout,ArrayList<RepairShopReputeProfile> arrayList){
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        RepairShopReputeProfile profile = arrayList.get(position);

        TextView textName = (TextView)convertView.findViewById(R.id.textName);
        textName.setText(profile.getName());

        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.parseFloat(profile.getRating()));
        float rating = Float.parseFloat(profile.getRating());
        //ratingBar.setBackgroundColor(Color.YELLOW);
        ratingBar.setRating(rating);
        ratingBar.setClickable(false);
        stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.SRC_ATOP);


        TextView textTitle = (TextView)convertView.findViewById(R.id.textTitle);
        textTitle.setText(profile.getTitle());

        TextView textRepute = (TextView)convertView.findViewById(R.id.textRepute);
        textRepute.setText(profile.getRepute());

        return convertView;
    }
}
