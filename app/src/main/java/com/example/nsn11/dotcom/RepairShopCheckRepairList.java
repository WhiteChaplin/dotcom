package com.example.nsn11.dotcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class RepairShopCheckRepairList extends AppCompatActivity {
    TextView textHello;
    LinearLayout linear;
    ListView listView;
    Toolbar toolbar;
    private ArrayList<FinalEstimate> finalEstimate = new ArrayList<FinalEstimate>();
    private ArrayList<Estimate> estimate = new ArrayList<Estimate>();
    private ArrayList<String> estimateArray = new ArrayList<String>();
    private ArrayList<String> finalEstimateArray = new ArrayList<String>();
    private ArrayList<RepairShopProfile> profile = new ArrayList<RepairShopProfile>();
    //private ArrayList<String> estimateNumberArray = new ArrayList<String>();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    String userName;
    String UID;
    String estimateNumber;
    String str;
    DatabaseReference repairShopInfomation=null;
    DatabaseReference repairShopInfomation_get_RepairShopID=null;
    DatabaseReference repairShopInfomation3=null;
    DatabaseReference get_user_Estimate=null;
    DatabaseReference get_repair_Shop_Estimate=null;
    DatabaseReference estimate_Infomation=null;

    String estimate_explainText;
    String estimate_locationText;
    String estimate_moreOption;
    String estimate_pictureFilePath;
    String estimate_selfRepairText;
    String estimate_separateText;
    String estimate_userUID;

    String final_Estimate_forecastWrong;
    String final_Estimate_pay;
    String final_Estimate_repairDetail;
    String final_Estimate_repairShopName;
    String final_Estimate_repairTime;
    String final_Estimate_repairWay;
    String final_Estimate_userID;

    String getKey=null;
    long userCount;
    DatabaseReference estimate_Num_Count=null;
    String nowState;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_shop_check_repair_list);
        listView = (ListView)findViewById(R.id.list);
        //textHello = (TextView)findViewById(R.id.textHello);
        //linear = (LinearLayout)findViewById(R.id.linear);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        UID = firebaseUser.getUid();

        repairShopInfomation = firebaseDatabase.getReference("users").child(UID.toString());
        repairShopInfomation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("userName").getValue(String.class);
                repairShopInfomation_get_RepairShopID = firebaseDatabase.getReference("shop_Infomation");
                repairShopInfomation_get_RepairShopID.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if(dataSnapshot1.child("shopName").getValue(String.class).equals(userName.toString())){
                                getKey = dataSnapshot1.getKey().toString();
                                //Toast.makeText(getApplicationContext(),getKey.toString(),Toast.LENGTH_SHORT).show();


                                repairShopInfomation3 = firebaseDatabase.getReference("shop_Infomation").child(getKey.toString()).child("vision_My_Repair_Estimate_Number");
                                repairShopInfomation3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        userCount = dataSnapshot.getChildrenCount();
                                        for(DataSnapshot dataSnapshop3 : dataSnapshot.getChildren()) {
                                            estimateNumber = dataSnapshop3.child("estimateNum").getValue(String.class);
                                            estimateArray.add(estimateNumber.toString());
                                        }

                                        for(int i=0;i<estimateArray.size();i++){
                                            str +=estimateArray.get(i).toString();
                                        }
                                        //Toast.makeText(getApplicationContext(),str.toString(),Toast.LENGTH_SHORT).show();


                                        estimate_Infomation = firebaseDatabase.getReference("user_Estimate");
                                        for(int i=0;i<estimateArray.size();i++) {
                                            //Toast.makeText(getApplicationContext(),estimateNumber.toString(),Toast.LENGTH_SHORT).show();   //정상작동
                                            get_user_Estimate = firebaseDatabase.getReference("user_Estimate").child(estimateArray.get(i).toString());
                                            int finalI = i;
                                            get_user_Estimate.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    get_repair_Shop_Estimate = firebaseDatabase.getReference("repair_Shop_Estimate").child(estimateArray.get(finalI).toString());
                                                    //정상작동
                                                    //Toast toast = Toast.makeText(getApplicationContext(),dataSnapshot.child("separateText").getValue(String.class).toString(),Toast.LENGTH_LONG);
                                                    //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,0);
                                                    //toast.show();
                                                    estimate.add(new Estimate(dataSnapshot.child("userUID").getValue(String.class), dataSnapshot.child("pictureFilePath").getValue(String.class), dataSnapshot.child("explainEditText").getValue(String.class),
                                                            dataSnapshot.child("editTitle").getValue(String.class),dataSnapshot.child("separateText").getValue(String.class), dataSnapshot.child("selfRepairText").getValue(String.class), dataSnapshot.child("locationText").getValue(String.class),
                                                            dataSnapshot.child("moreOption").getValue(String.class),dataSnapshot.child("date").getValue(String.class)));


                                                    get_repair_Shop_Estimate.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                String repairShopName = dataSnapshot1.child("repairShopName").getValue(String.class);
                                                                int index = repairShopName.toString().indexOf("/");
                                                                String trim_repairShopName = repairShopName.toString().substring(0, index);
                                                                if (userName.toString().equals(trim_repairShopName.toString())) {
                                                                    //정상작동
                                                                    //Toast toast = Toast.makeText(getApplicationContext(),dataSnapshot1.child("pay").getValue(String.class),Toast.LENGTH_LONG);
                                                                    //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.RIGHT,0,0);
                                                                    //toast.show();

                                                                    finalEstimate.add(new FinalEstimate(dataSnapshot1.child("forecastWrong").getValue(String.class), dataSnapshot1.child("pay").getValue(String.class), dataSnapshot1.child("repairDetail").getValue(String.class),
                                                                            dataSnapshot1.child("repairTime").getValue(String.class), dataSnapshot1.child("userID").getValue(String.class), dataSnapshot1.child("estimateNumber").getValue(String.class),
                                                                            dataSnapshot1.child("repairShopName").getValue(String.class), dataSnapshot1.child("repairWay").getValue(String.class),dataSnapshot1.child("editTitle").getValue(String.class)));
                                                                }

                                                            }

                                                            estimate_Infomation.child(estimateArray.get(finalI).toString()).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    profile.add(new RepairShopProfile(dataSnapshot.child("editTitle").getValue(String.class),dataSnapshot.child("separateText").getValue(String.class),dataSnapshot.child("date").getValue(String.class),dataSnapshot.child("nowState").getValue(String.class),dataSnapshot.getKey().toString()));
                                                                    RepairShopProfileListAdapter adapter = new RepairShopProfileListAdapter(getApplicationContext(),R.layout.repair_profile_view,profile);
                                                                    nowState = dataSnapshot.child("nowState").getValue(String.class);
                                                                    listView.setAdapter(adapter);

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

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent intent = new Intent(RepairShopCheckRepairList.this,Check_Repair_List.class);
                                                finalEstimateArray.add(0, finalEstimate.get(position).forecastWrong);
                                                finalEstimateArray.add(1, finalEstimate.get(position).pay);
                                                finalEstimateArray.add(2, finalEstimate.get(position).repairDetail);
                                                finalEstimateArray.add(3, finalEstimate.get(position).repairTime);
                                                finalEstimateArray.add(4, finalEstimate.get(position).userID);
                                                finalEstimateArray.add(5, finalEstimate.get(position).estimateNumber);
                                                finalEstimateArray.add(6, finalEstimate.get(position).repairShopName);
                                                finalEstimateArray.add(7, finalEstimate.get(position).repairWay);

                                                estimateArray.add(0, estimate.get(position).userUID);
                                                estimateArray.add(1, estimate.get(position).pictureFilePath);
                                                estimateArray.add(2, estimate.get(position).explainEditText);
                                                estimateArray.add(3, estimate.get(position).separateText);
                                                estimateArray.add(4, estimate.get(position).selfRepairText);
                                                estimateArray.add(5, estimate.get(position).locationText);
                                                estimateArray.add(6, estimate.get(position).moreOption);
                                                estimateArray.add(7, estimate.get(position).date);

                                                intent.putStringArrayListExtra("finalEstimateArray",finalEstimateArray);
                                                intent.putStringArrayListExtra("estimateArray",estimateArray);
                                                intent.putExtra("nowState",nowState.toString());
                                                startActivity(intent);
                                            }
                                        });
                                    }



                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
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
