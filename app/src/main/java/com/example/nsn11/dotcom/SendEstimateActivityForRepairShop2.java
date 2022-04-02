package com.example.nsn11.dotcom;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Time;
import java.time.Clock;
import java.time.Year;
import java.util.Calendar;

public class SendEstimateActivityForRepairShop2 extends AppCompatActivity {
    TextView textViewForecastWrong;
    TextView textViewPay;
    TextView textViewRepairWay;
    TextView textViewRepairDetail;
    TextView textViewRepairTakeTime;
    TextView UserUID;
    TextView textUserUID,EstimateNum,textEstimateNum,ShopName,textShopName,Date,textDate;
    EditText editForecastWrong,editPay,editRepairWay,editRepairDetail,editRepairTakeTime;
    Button btnSendEstimate,btnCheckDay;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference shopName;
    String customer_UID;
    String EstimateNumber;
    String stringEstimateNum;
    String repairShopName;
    String str_Date;
    String user_Estimate_Key;
    String title;
    int m_year;
    int m_month;
    int m_day;
    String setDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_estimate_activity_for_repair_shop);
        textViewForecastWrong = (TextView)findViewById(R.id.textViewForecastWrong);
        textViewPay = (TextView)findViewById(R.id.textViewPay);
        textViewRepairWay = (TextView)findViewById(R.id.textViewRepairWay);
        textViewRepairDetail= (TextView)findViewById(R.id.textViewRepairDetail);
        textViewRepairTakeTime = (TextView)findViewById(R.id.textViewRepairTakeTime);
        UserUID = (TextView)findViewById(R.id.UserUID);
        textUserUID= (TextView)findViewById(R.id.textUserUID);
        editForecastWrong = (EditText)findViewById(R.id.editForecastWrong);
        editPay = (EditText)findViewById(R.id.editPay);
        editRepairWay= (EditText)findViewById(R.id.editRepairWay);
        editRepairDetail = (EditText)findViewById(R.id.editRepairDetail);
        //editRepairTakeTime= (EditText)findViewById(R.id.editRepairTakeTime);
        btnSendEstimate = (Button)findViewById(R.id.btnSendEstimate);
        btnCheckDay = (Button)findViewById(R.id.btnCheckDay);
        EstimateNum = (TextView)findViewById(R.id.EstimateNum);
        textEstimateNum = (TextView)findViewById(R.id.textEstimateNum);
        ShopName = (TextView)findViewById(R.id.ShopName);
        textShopName = (TextView)findViewById(R.id.textShopName);
        Date = (TextView)findViewById(R.id.Date);
        textDate = (TextView)findViewById(R.id.textDate);

        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent getIntent = getIntent();
        customer_UID = getIntent.getExtras().getString("UserUID","");
        EstimateNumber = getIntent.getExtras().getString("EstimateNum","");
        stringEstimateNum = getIntent.getExtras().getString("EstimateNum","");
        repairShopName = getIntent.getExtras().getString("repairShopName","");
        str_Date = getIntent.getExtras().getString("date","");
        title = getIntent.getExtras().getString("title","");
        //int index = repairShopName.indexOf("/");
        //repairShopName.substring(0,index);
        textUserUID.setText(customer_UID.toString());
        textEstimateNum.setText(EstimateNumber.toString());
        textShopName.setText(repairShopName.toString());
        textDate.setText(str_Date.toString());



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        editPay.setText("원");
        //editRepairTakeTime.setText("일");

        //getShopName();
        Calendar calendar = Calendar.getInstance();

        btnCheckDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(SendEstimateActivityForRepairShop2.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnCheckDay.setText(year+"년"+" "+String.valueOf(month+1)+"월"+" "+dayOfMonth+"일로 설정되었습니다");
                        setDate = year+"년"+" "+String.valueOf(month+1)+"월"+" "+dayOfMonth+"일";
                        Toast.makeText(getApplicationContext(),setDate.toString(),Toast.LENGTH_SHORT).show();
                    }
                },calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DATE));

                dialog.show();
            }
        });


        btnSendEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editForecastWrong.getText().toString().equals("") && !editRepairWay.getText().toString().equals("") && !editRepairDetail.getText().toString().equals("")
                        && !setDate.toString().equals("") && !editPay.getText().toString().equals("")){
                    final FinalEstimate finalEstimate = new FinalEstimate();
                    final EstimateNum estimateNum = new EstimateNum();

                    finalEstimate.forecastWrong = editForecastWrong.getText().toString();
                    finalEstimate.pay = editPay.getText().toString();
                    finalEstimate.repairTime = setDate.toString();
                    finalEstimate.repairDetail = editRepairDetail.getText().toString();
                    finalEstimate.userID = customer_UID.toString();
                    finalEstimate.estimateNumber = textEstimateNum.getText().toString();
                    finalEstimate.repairShopName = repairShopName.toString();
                    finalEstimate.repairWay = editRepairWay.getText().toString();
                    finalEstimate.title = title.toString();

                    finalEstimate.setForecastWrong(editForecastWrong.getText().toString());
                    finalEstimate.setPay(editPay.getText().toString());
                    finalEstimate.setRepairDetail(editRepairDetail.getText().toString());
                    finalEstimate.setRepairTime(setDate.toString());
                    finalEstimate.setEstimateNumber(textEstimateNum.getText().toString());
                    finalEstimate.setUserID(customer_UID.toString());
                    finalEstimate.setRepairShopName(repairShopName.toString());
                    finalEstimate.setRepairWay(editRepairWay.getText().toString());
                    finalEstimate.setTitle(title.toString());

                    //estimateNum.estimateNum = EstimateNumber.toString();
                    //estimateNum.setEstimateNum(EstimateNumber.toString());
                    estimateNum.userID = firebaseUser.getUid().toString();
                    estimateNum.setUserID(firebaseUser.getUid().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(customer_UID.toString()).child("user_Estimate").child("user_Estimate_Number_check_repairShop").child(stringEstimateNum.toString()).child("shopID").push().setValue(estimateNum);
                    //nowState값만 변경하는 코드
                    FirebaseDatabase.getInstance().getReference().child("user_Estimate").child(stringEstimateNum).child("nowState").setValue("수리점 확인 완료");
                    FirebaseDatabase.getInstance().getReference().child("repair_Shop_Estimate").child(stringEstimateNum.toString()).push().setValue(finalEstimate).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SendEstimateActivityForRepairShop2.this);
                                    builder.setTitle("견적 전송 완료!");
                                    builder.setMessage("견적이 유저에게 전송이 완료되었습니다!");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(SendEstimateActivityForRepairShop2.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                    }
                else{
                    Toast.makeText(getApplicationContext(),"입력하지 않은 칸이 있습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void getShopName()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        shopName = firebaseDatabase.getReference("users").child(customer_UID.toString());
        shopName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //repairShopName = dataSnapshot.child("userName").getValue(String.class);
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

    void shopDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                m_year = year;
                m_month = month+1;
                m_day = dayOfMonth;
            }
        },m_year,m_month,m_day);
    }
}
