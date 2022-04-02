package com.example.nsn11.dotcom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.support.v4.widget.NestedScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RepairShopInfomationDetailActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView btn_close,imgview_shoppicture;
    Toolbar toolbar;
    TextView testText,textShopName,textShopLocation,textRepairCount,textCustomerRepute,textCustomerReview,textCount,textRepute,textReview;
    String shopID;
    NestedScrollView scrollView,scrollMain;
    long repair_Count;
    ArrayList<Double> repute_sum = new ArrayList<Double>();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference get_Shop_repair_Repute_count=null;
    DatabaseReference get_Shop_repair_Repute_Rating=null;
    DatabaseReference get_Shop_repairList=null;
    DatabaseReference get_Shop_Name=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_shop_infomation_detail);

        shopID = getIntent().getExtras().getString("shopID");
        //Toast.makeText(getApplicationContext(),shopID.toString(),Toast.LENGTH_SHORT).show();

        textShopName = (TextView)findViewById(R.id.textShopName);
        textShopLocation = (TextView)findViewById(R.id.textShopLocation);
        textRepairCount = (TextView)findViewById(R.id.textRepairCount);
        textCustomerRepute = (TextView)findViewById(R.id.textCustomerRepute);
        textCustomerReview = (TextView)findViewById(R.id.textCustomerReview);
        textCount = (TextView)findViewById(R.id.textCount);
        textRepute = (TextView)findViewById(R.id.textRepute);
        textReview = (TextView)findViewById(R.id.textReview);
        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        btn_close = (ImageView)findViewById(R.id.btn_close);
        imgview_shoppicture = (ImageView)findViewById(R.id.imgview_shoppicture);
        //scrollView = (NestedScrollView) findViewById(R.id.scrollViewpager);
        //scrollMain = (NestedScrollView) findViewById(R.id.ScrollMain);
        viewPager = (ViewPager)findViewById(R.id.pager);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        imgview_shoppicture.bringToFront();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        get_Shop_Name = firebaseDatabase.getReference("shop_Infomation");
        get_Shop_Name.child(shopID.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textShopName.setText(dataSnapshot.child("shopName").getValue(String.class));
                textShopLocation.setText(dataSnapshot.child("shopCity").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        get_Shop_repair_Repute_count = firebaseDatabase.getReference("shop_Infomation");
        get_Shop_repair_Repute_count.child(shopID.toString()).child("shop_Repute").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textReview.setText(String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        get_Shop_repairList = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString()).child("vision_My_Repair_Estimate_Number");
        get_Shop_repairList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                repair_Count = dataSnapshot.getChildrenCount();
                textCount.setText(String.valueOf(repair_Count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        get_Shop_repair_Repute_Rating = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString()).child("shop_Repute");
        get_Shop_repair_Repute_Rating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String repute;
                    if(dataSnapshot1.child("repute_Rating").getValue(String.class).equals("")){
                        return;
                    }
                    else{
                        repute = dataSnapshot1.child("repute_Rating").getValue(String.class);
                        //Toast.makeText(getApplicationContext(),repute.toString(),Toast.LENGTH_SHORT).show();
                        Double repute_double = Double.valueOf(repute);
                        repute_sum.add(repute_double);
                        //Toast.makeText(getApplicationContext(),repute_double.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                Double sum = 0.0;
                Double average;
                for(int i=0;i<repute_sum.size();i++){
                    sum +=repute_sum.get(i);
                }
                average = sum/repute_sum.size();
                String ave = String.format("%.2f",average);
                if(ave.equals(Double.NaN)){
                    textRepute.setText("0점");
                }
                else{
                    textRepute.setText(ave.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("소개"));
        tabLayout.addTab(tabLayout.newTab().setText("후기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        Bundle bundle = new Bundle();
        bundle.putString("shopID",shopID.toString());
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),bundle);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
