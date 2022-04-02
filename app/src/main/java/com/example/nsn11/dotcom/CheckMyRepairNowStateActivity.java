package com.example.nsn11.dotcom;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckMyRepairNowStateActivity extends AppCompatActivity {
    ImageView image1, image2, image3, image4, line, shopImage;
    TextView textShopName,textTitle,textViewPay,textPay,textViewForecastDay,textForecastDay,textGotoEstimate, text1, text2, text3, text4;
    RelativeLayout layout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference get_My_EsimateNumber = null;
    DatabaseReference get_My_State = null;
    DatabaseReference get_Repair_Estimate = null;
    DatabaseReference get_Repair_Separate = null;
    String UID;
    String str;
    String str_estimateNum;
    Toolbar toolbar;
    String separate;
    private ArrayList<String> estimateNum = new ArrayList<String>();
    ArrayList<String> estimateArray = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_my_repair_now_state);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        line = (ImageView)findViewById(R.id.line);
        shopImage = (ImageView)findViewById(R.id.shopImage);
        textShopName = (TextView)findViewById(R.id.textShopName);
        textTitle = (TextView)findViewById(R.id.textTitle);
        textViewPay = (TextView)findViewById(R.id.textViewPay);
        textPay = (TextView)findViewById(R.id.textPay);
        textViewForecastDay = (TextView)findViewById(R.id.textViewForecastDay);
        textForecastDay = (TextView)findViewById(R.id.textForecastDay);
        textGotoEstimate = (TextView)findViewById(R.id.textGotoEstimate);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
        text3= (TextView)findViewById(R.id.text3);
        text4= (TextView)findViewById(R.id.text4);
        layout = (RelativeLayout)findViewById(R.id.layout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        UID = firebaseUser.getUid();

        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        get_My_EsimateNumber = firebaseDatabase.getReference("users").child(UID.toString()).child("user_Estimate").child("user_Estimate_Number");
        get_My_EsimateNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(),dataSnapshot.getChildren().toString(),Toast.LENGTH_SHORT).show();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            str_estimateNum = dataSnapshot2.child("estimateNum").getValue(String.class);
                            estimateNum.add(str_estimateNum);
                            //Toast.makeText(getApplicationContext(),str_estimateNum.toString(),Toast.LENGTH_SHORT).show();
                        }
                        for(int i=0;i<estimateNum.size();i++)
                        {
                            str+=estimateNum.get(i).toString()+" ";
                        }
                        //Toast.makeText(getApplicationContext(),estimateNum.get(estimateNum.size()-1).toString(),Toast.LENGTH_SHORT).show();


                        get_My_State = firebaseDatabase.getReference("user_Estimate").child(estimateNum.get(estimateNum.size()-1).toString());
                        get_My_State.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                shopImage.setVisibility(View.VISIBLE);
                                textShopName.setVisibility(View.VISIBLE);
                                textGotoEstimate.setVisibility(View.VISIBLE);
                                textViewPay.setVisibility(View.VISIBLE);
                                textPay.setVisibility(View.VISIBLE);
                                textViewForecastDay.setVisibility(View.VISIBLE);
                                textForecastDay.setVisibility(View.VISIBLE);
                                textTitle.setVisibility(View.VISIBLE);
                                layout.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),estimateNum.get(estimateNum.size()-1),Toast.LENGTH_SHORT).show();
                                Log.e("estimateNum",estimateNum.get(estimateNum.size()-1));
                                String state = dataSnapshot.child("nowState").getValue(String.class);
                                textShopName.setText(dataSnapshot.child("locationText").getValue(String.class));
                                textTitle.setText(dataSnapshot.child("editTitle").getValue(String.class));
                                if(state.toString().equals("수리점 확인 완료")){
                                    image1.setImageResource(R.drawable.pot_yellow);
                                    image2.setImageResource(R.drawable.pot_black);
                                    image3.setImageResource(R.drawable.pot_black);
                                    image4.setImageResource(R.drawable.pot_black);
                                }
                                else if(state.toString().equals("수리 진행중")){
                                    image1.setImageResource(R.drawable.pot_yellow);
                                    image2.setImageResource(R.drawable.pot_yellow);
                                    image3.setImageResource(R.drawable.pot_black);
                                    image4.setImageResource(R.drawable.pot_black);
                                }
                                else if(state.toString().equals("수리 완료!")){
                                    image1.setImageResource(R.drawable.pot_yellow);
                                    image2.setImageResource(R.drawable.pot_yellow);
                                    image3.setImageResource(R.drawable.pot_yellow);
                                    image4.setImageResource(R.drawable.pot_black);
                                }
                                else if(state.toString().equals("수리 평가 완료")){
                                    image1.setImageResource(R.drawable.pot_yellow);
                                    image2.setImageResource(R.drawable.pot_yellow);
                                    image3.setImageResource(R.drawable.pot_yellow);
                                    image4.setImageResource(R.drawable.pot_yellow);
                                }

                                get_Repair_Separate = firebaseDatabase.getReference("user_Estimate").child(estimateNum.get(estimateNum.size()-1));
                                get_Repair_Separate.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        separate = dataSnapshot.child("separateText").getValue(String.class);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                get_Repair_Estimate = firebaseDatabase.getReference("repair_Shop_Estimate").child(estimateNum.get(estimateNum.size()-1));
                                get_Repair_Estimate.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                                            textPay.setText(dataSnapshot2.child("pay").getValue(String.class));
                                            textForecastDay.setText(dataSnapshot2.child("repairTime").getValue(String.class));


                                            estimateArray.add(0,dataSnapshot2.child("forecastWrong").getValue(String.class));
                                            estimateArray.add(1,dataSnapshot2.child("pay").getValue(String.class));
                                            estimateArray.add(2,dataSnapshot2.child("repairWay").getValue(String.class));
                                            estimateArray.add(3,dataSnapshot2.child("repairDetail").getValue(String.class));
                                            estimateArray.add(4,dataSnapshot2.child("repairTime").getValue(String.class));
                                            estimateArray.add(5,dataSnapshot2.child("userID").getValue(String.class));
                                            estimateArray.add(6,dataSnapshot2.child("estimateNumber").getValue(String.class));
                                            estimateArray.add(7,dataSnapshot2.child("repairShopName").getValue(String.class));
                                            estimateArray.add(8,dataSnapshot2.child("title").getValue(String.class));


                                            textGotoEstimate.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //Toast.makeText(getApplicationContext(),"일단 눌림",Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(CheckMyRepairNowStateActivity.this,SendEstimateActivityForCustomer.class);
                                                    intent.putStringArrayListExtra("Array",estimateArray);
                                                    intent.putExtra("separateText",separate.toString());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}