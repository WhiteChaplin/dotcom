package com.example.nsn11.dotcom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

public class UserCheckEstimateActivity extends AppCompatActivity {
    //작성한 요청서에 대한 수리점의 결과를 받아오는 액티비티
    TextView textHello,text1,text2,text3,text4,text5,text6,text7,text8;
    Toolbar toolbar;
    LinearLayout linear;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference user_info_reference = null;
    DatabaseReference estimate_numRef=null;
    DatabaseReference estimate_Infomation=null;
    DatabaseReference estimate_num_check_repairShop=null;
    DatabaseReference estimate_Num_Count=null;
    String userName;
    String Estimate_Path;
    String UID;
    String str;
    String forecastWrong,pay,repairDetail,repairTime,userID,estimateNumber,repairShopName,repairWay;
    private ArrayList<FinalEstimate> finalEstimates = new ArrayList<FinalEstimate>();
    private ArrayList<String> finalEstimateString = new ArrayList<String>();
    private ArrayList<String> estimateNum = new ArrayList<String>();
    private ArrayList<Profile> profile = new ArrayList<Profile>();
    String str_estimateNum;
    long userCount=0;
    long userCount2=0;
    int userCountTest;
    int userCount2Test;
    ListView listView;
    String separateText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_check_estimate);
        listView = (ListView)findViewById(R.id.user_check_estimate_list);

        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = new Intent(UserCheckEstimateActivity.this,SendEstimateActivityForCustomer.class);

        //linear = (LinearLayout)findViewById(R.id.linear);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        UID = firebaseUser.getUid();

        final Intent getIntent = getIntent();
        userName = getIntent.getExtras().getString("userName");


        user_info_reference = firebaseDatabase.getReference("users").child(UID.toString()).child("user_Estimate").child("user_Estimate_Number");
        if(!user_info_reference.equals("")){
            //Toast.makeText(getApplicationContext(),user_info_reference.toString(),Toast.LENGTH_LONG).show();
            //견적 요청 갯수로 들어가는 reference
            user_info_reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            str_estimateNum = dataSnapshot2.child("estimateNum").getValue(String.class);
                            estimateNum.add(str_estimateNum);
                            //Toast.makeText(getApplicationContext(),str_estimateNum.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    for(int i=0;i<estimateNum.size();i++)
                    {
                        str+=estimateNum.get(i).toString()+" ";
                    }
                    //견적서 리스트 잘 넘어옴
                    //Toast.makeText(getApplicationContext(),str.toString(),Toast.LENGTH_SHORT).show();

                    //견적 요청 갯수
                    userCount2 = dataSnapshot.getChildrenCount();


                    estimate_numRef = firebaseDatabase.getReference("repair_Shop_Estimate");
                    for(int i=0;i<estimateNum.size();i++){
                        estimate_numRef.child(estimateNum.get(i).toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    if(!dataSnapshot1.child("forecastWrong").getValue(String.class).equals("")){
                                        finalEstimates.add(new FinalEstimate(dataSnapshot1.child("forecastWrong").getValue(String.class), dataSnapshot1.child("pay").getValue(String.class), dataSnapshot1.child("repairDetail").getValue(String.class),
                                                dataSnapshot1.child("repairTime").getValue(String.class), dataSnapshot1.child("userID").getValue(String.class), dataSnapshot1.child("estimateNumber").getValue(String.class),
                                                dataSnapshot1.child("repairShopName").getValue(String.class), dataSnapshot1.child("repairWay").getValue(String.class),dataSnapshot1.child("title").getValue(String.class)));
                                    }
                                    else{

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    //요쳥 견적에 대한 수리점 제안서 갯수
                    userCount = dataSnapshot.getChildrenCount();


                    estimate_Infomation = firebaseDatabase.getReference("user_Estimate");
                    for(int i=0;i<estimateNum.size();i++){
                        int finalI = i;
                        estimate_Infomation.child(estimateNum.get(i).toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                profile.add(new Profile(dataSnapshot.child("editTitle").getValue(String.class),dataSnapshot.child("separateText").getValue(String.class),dataSnapshot.child("date").getValue(String.class),dataSnapshot.child("nowState").getValue(String.class),dataSnapshot.getKey().toString()));

                                separateText = dataSnapshot.child("separateText").getValue(String.class);

                                ProfileListAdapter adapter = new ProfileListAdapter(getApplicationContext(),R.layout.repair_profile_view,profile);

                                listView.setAdapter(adapter);
                                listView.setClickable(true);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(profile.get(position).getState().equals("수리점 확인중")){
                                Toast.makeText(getApplicationContext(),"수리점이 확인중에 있습니다. 조금만 기다려주세요",Toast.LENGTH_SHORT).show();
                            }
                            else if(profile.get(position).getState().equals("수리점 확인 완료")){
                                //Toast.makeText(getApplicationContext(),profile.get(position).getTitle()+" "+profile.get(position).getEstimateNumber()+" "+profile.get(position).getState(),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                                if(finalEstimates.size()==position){
                                    Toast.makeText(getApplicationContext(),"범위가 더 커요",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Toast.makeText(getApplicationContext(),finalEstimates.get(position).forecastWrong.toString(),Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(),String.valueOf(finalEstimates.size()),Toast.LENGTH_SHORT).show();
                                    finalEstimateString.add(0,finalEstimates.get(position).forecastWrong);
                                    finalEstimateString.add(1,finalEstimates.get(position).pay);
                                    finalEstimateString.add(2,finalEstimates.get(position).repairWay);
                                    finalEstimateString.add(3,finalEstimates.get(position).repairDetail);
                                    finalEstimateString.add(4,finalEstimates.get(position).repairTime);
                                    finalEstimateString.add(5,finalEstimates.get(position).userID);
                                    finalEstimateString.add(6,finalEstimates.get(position).estimateNumber);
                                    finalEstimateString.add(7,finalEstimates.get(position).repairShopName);
                                    finalEstimateString.add(8,finalEstimates.get(position).title);
                                    intent.putStringArrayListExtra("Array",finalEstimateString);
                                    intent.putExtra("separateText",separateText.toString());
                                    startActivity(intent);
                                }
                            }
                            else if(profile.get(position).getState().equals("수리 진행중")){
                                //Toast.makeText(getApplicationContext(),profile.get(position).getTitle()+" "+profile.get(position).getEstimateNumber()+" "+profile.get(position).getState(),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserCheckEstimateActivity.this,SendEstimateActivityForCustomer.class);
                                if(finalEstimates.size()==position){
                                    Toast.makeText(getApplicationContext(),"범위가 더 커요",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Toast.makeText(getApplicationContext(),finalEstimates.get(position).forecastWrong.toString(),Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(),String.valueOf(finalEstimates.size()),Toast.LENGTH_SHORT).show();
                                    finalEstimateString.add(0,finalEstimates.get(position).forecastWrong);
                                    finalEstimateString.add(1,finalEstimates.get(position).pay);
                                    finalEstimateString.add(2,finalEstimates.get(position).repairWay);
                                    finalEstimateString.add(3,finalEstimates.get(position).repairDetail);
                                    finalEstimateString.add(4,finalEstimates.get(position).repairTime);
                                    finalEstimateString.add(5,finalEstimates.get(position).userID);
                                    finalEstimateString.add(6,finalEstimates.get(position).estimateNumber);
                                    finalEstimateString.add(7,finalEstimates.get(position).repairShopName);
                                    finalEstimateString.add(8,finalEstimates.get(position).title);
                                    intent.putStringArrayListExtra("Array",finalEstimateString);
                                    intent.putExtra("separateText",separateText.toString());
                                    startActivity(intent);
                                }
                            }
                            else if(profile.get(position).getState().equals("수리 완료!")){
                                //Toast.makeText(getApplicationContext(),profile.get(position).getTitle()+" "+profile.get(position).getEstimateNumber()+" "+profile.get(position).getState(),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserCheckEstimateActivity.this,SendEstimateActivityForCustomer.class);
                                if(finalEstimates.size()==position){
                                    Toast.makeText(getApplicationContext(),"범위가 더 커요",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Toast.makeText(getApplicationContext(),finalEstimates.get(position).forecastWrong.toString(),Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(),String.valueOf(finalEstimates.size()),Toast.LENGTH_SHORT).show();
                                    finalEstimateString.add(0,finalEstimates.get(position).forecastWrong);
                                    finalEstimateString.add(1,finalEstimates.get(position).pay);
                                    finalEstimateString.add(2,finalEstimates.get(position).repairWay);
                                    finalEstimateString.add(3,finalEstimates.get(position).repairDetail);
                                    finalEstimateString.add(4,finalEstimates.get(position).repairTime);
                                    finalEstimateString.add(5,finalEstimates.get(position).userID);
                                    finalEstimateString.add(6,finalEstimates.get(position).estimateNumber);
                                    finalEstimateString.add(7,finalEstimates.get(position).repairShopName);
                                    finalEstimateString.add(8,finalEstimates.get(position).title);
                                    intent.putStringArrayListExtra("Array",finalEstimateString);
                                    intent.putExtra("separateText",separateText.toString());
                                    startActivity(intent);
                                }
                            }
                            else if(profile.get(position).getState().equals("수리 평가 완료")){
                                //Toast.makeText(getApplicationContext(),profile.get(position).getTitle()+" "+profile.get(position).getEstimateNumber()+" "+profile.get(position).getState(),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserCheckEstimateActivity.this,SendEstimateActivityForCustomer.class);
                                if(finalEstimates.size()==position){
                                    Toast.makeText(getApplicationContext(),"범위가 더 커요",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Toast.makeText(getApplicationContext(),finalEstimates.get(position).forecastWrong.toString(),Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getApplicationContext(),String.valueOf(finalEstimates.size()),Toast.LENGTH_SHORT).show();
                                    finalEstimateString.add(0,finalEstimates.get(position).forecastWrong);
                                    finalEstimateString.add(1,finalEstimates.get(position).pay);
                                    finalEstimateString.add(2,finalEstimates.get(position).repairWay);
                                    finalEstimateString.add(3,finalEstimates.get(position).repairDetail);
                                    finalEstimateString.add(4,finalEstimates.get(position).repairTime);
                                    finalEstimateString.add(5,finalEstimates.get(position).userID);
                                    finalEstimateString.add(6,finalEstimates.get(position).estimateNumber);
                                    finalEstimateString.add(7,finalEstimates.get(position).repairShopName);
                                    finalEstimateString.add(8,finalEstimates.get(position).title);
                                    intent.putStringArrayListExtra("Array",finalEstimateString);
                                    intent.putExtra("separateText",separateText.toString());
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(user_info_reference.equals(firebaseDatabase.getReference("users").child(UID.toString()))){
            Toast.makeText(getApplicationContext(),"요청한 견적서가 없습니다. 수리점에게 수리를 요청해보세요!",Toast.LENGTH_SHORT).show();
        }



        //textHello.setText("안녕하세요 "+userName.toString()+"님!\n"+userName.toString()+"님의 견적서 목록입니다!\n"+
                //"현재 고객님의 견적요청 목록은 "+userCount+" 개 입니다\n"+"userCount2:"+userCount2);
        /*
        Button btn[] = new Button[(int) userCount2];
        //Button btn[] = new Button[10];
        for(int j=0;j<(int)userCount2;j++)
        {
            btn[j] = new Button(getApplicationContext());
            btn[j].setText(j+1+"번째 견적서");
            linear.addView(btn[j]);
            Toast.makeText(getApplicationContext(),j+1+"번째 견적서",Toast.LENGTH_SHORT).show();

            final int finalJ = j;
            btn[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalEstimateString.add(0,finalEstimates.get(finalJ).forecastWrong);
                    finalEstimateString.add(1,finalEstimates.get(finalJ).pay);
                    finalEstimateString.add(2,finalEstimates.get(finalJ).repairWay);
                    finalEstimateString.add(3,finalEstimates.get(finalJ).repairDetail);
                    finalEstimateString.add(4,finalEstimates.get(finalJ).repairTime);
                    finalEstimateString.add(5,finalEstimates.get(finalJ).userID);
                    finalEstimateString.add(6,finalEstimates.get(finalJ).estimateNumber);
                    finalEstimateString.add(7,finalEstimates.get(finalJ).repairShopName);

                    Intent intent = new Intent(UserCheckEstimateActivity.this,SendEstimateActivityForCustomer.class);
                    intent.putStringArrayListExtra("Array",finalEstimateString);
                    startActivity(intent);
                }
            });
        }
        */
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
