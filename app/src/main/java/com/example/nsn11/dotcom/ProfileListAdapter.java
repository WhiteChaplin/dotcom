package com.example.nsn11.dotcom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Profile> arrayList;
    private int layout;

    public  ProfileListAdapter(Context context,int layout,ArrayList<Profile> arrayList){
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

        Profile profile = arrayList.get(position);

        convertView.setClickable(false);

        TextView textTitle = (TextView)convertView.findViewById(R.id.textTitle);
        textTitle.setText(profile.getTitle());

        TextView textClass = (TextView)convertView.findViewById(R.id.textChoice);
        textClass.setText(profile.getChoice());

        TextView textDate = (TextView)convertView.findViewById(R.id.textTime);
        textDate.setText(profile.getDate());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.profile_Image);

        TextView textState = (TextView)convertView.findViewById(R.id.textState);
        textState.setText(profile.getState());


        if(profile.getState().equals("수리점 확인중")){
            imageView.setVisibility(View.INVISIBLE);
        }
        else if(!profile.getState().equals("수리점 확인중")){
            imageView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
