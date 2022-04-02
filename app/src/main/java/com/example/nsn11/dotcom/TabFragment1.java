package com.example.nsn11.dotcom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TabFragment1 extends Fragment {
    View view=null;
    Context context=null;
    TextView textShopIntroduce,textShopService,textShopTime,textWeekday,textWeekdayTime,textSaturday,textSaturdayTime,textSunday,
            textSundayTime,textViewTel,textViewKakaoTalk,textKakaoTalk,textViewPhoneNumber,textPhoneNumber;
    String shopID;
    Button btnUID;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference get_Shop_Infomation=null;
    DatabaseReference get_Shop_Name=null;
    DatabaseReference get_Shop_repairList=null;
    DatabaseReference get_Shop_repair_Repute_Rating=null;
    DatabaseReference get_Shop_repair_Repute_count=null;
    /*
    public static TabFragment1 newInstance(String shopID) {
        Bundle args = new Bundle();
        TabFragment1 fragment = new TabFragment1();
        //shopID = args.getString("shopID");
        args.putString("shopID",shopID.toString());
        fragment.setArguments(args);
        return fragment;
    }
     */




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //뷰 생성 작업
        view = inflater.inflate(R.layout.tab_fragment_1,container,false);
        context = container.getContext();

        //Firebase 적용 코드
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //shopID 값 가져오는 코드
        Bundle args = getArguments();
        shopID = args.getString("shopID","123");
        //Toast.makeText(context,shopID.toString()+" Tab1",Toast.LENGTH_SHORT).show();

        textShopIntroduce = (TextView)view.findViewById(R.id.textShopIntroduce);
        textShopService = (TextView)view.findViewById(R.id.textShopService);
        textShopTime = (TextView)view.findViewById(R.id.textShopTime);
        textWeekday = (TextView)view.findViewById(R.id.textWeekday);
        textWeekdayTime = (TextView)view.findViewById(R.id.textWeekdayTime);
        textSaturday = (TextView)view.findViewById(R.id.textSaturday);
        textSaturdayTime = (TextView)view.findViewById(R.id.textSaturdayTime);
        textSunday = (TextView)view.findViewById(R.id.textSunday);
        textSundayTime = (TextView)view.findViewById(R.id.textSundayTime);
        textViewTel = (TextView)view.findViewById(R.id.textViewTel);
        textViewKakaoTalk = (TextView)view.findViewById(R.id.textViewKakaoTalk);
        textKakaoTalk = (TextView)view.findViewById(R.id.textKakaoTalk);
        textViewPhoneNumber = (TextView)view.findViewById(R.id.textViewPhoneNumber);
        textPhoneNumber = (TextView)view.findViewById(R.id.textPhoneNumber);



        get_Shop_Infomation = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString());
        get_Shop_Infomation.child("text_Infomation_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textShopIntroduce.setText(dataSnapshot.child("repairShopExplain").getValue(String.class));
                textWeekdayTime.setText(dataSnapshot.child("repairShopWorkTimeWeekday").getValue(String.class));
                textSaturdayTime.setText(dataSnapshot.child("repairShopWorkTimeSaturday").getValue(String.class));
                textSundayTime.setText(dataSnapshot.child("repairShopWorkTimeSunday").getValue(String.class));
                textPhoneNumber.setText(dataSnapshot.child("shopTel").getValue(String.class));
                textKakaoTalk.setText(dataSnapshot.child("shopKakao").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
