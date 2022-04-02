package com.example.nsn11.dotcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SendEstimateActivityForRepairShop extends AppCompatActivity {
    TextView textViewForecastWrong;
    TextView textViewPay;
    TextView textViewRepairWay;
    TextView textViewRepairDetail;
    TextView textViewRepairTakeTime;
    TextView UserUID;
    TextView textUserUID,EstimateNum,textEstimateNum;
    EditText editForecastWrong,editPay,editRepairWay,editRepairDetail,editRepairTakeTime;
    Button btnSendEstimate;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String UID;
    String EstimateNumber;
    String stringEstimateNum;
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
        editRepairTakeTime= (EditText)findViewById(R.id.editRepairTakeTime);
        btnSendEstimate = (Button)findViewById(R.id.btnSendEstimate);
        EstimateNum = (TextView)findViewById(R.id.EstimateNum);
        textEstimateNum = (TextView)findViewById(R.id.textEstimateNum);

        Intent getIntent = getIntent();
        UID = getIntent.getExtras().getString("UserUID");
        EstimateNumber = getIntent.getExtras().getString("EstimateNum");
        stringEstimateNum = getIntent.getExtras().getString("EstimateNum");
        textUserUID.setText(UID.toString());
        textEstimateNum.setText(EstimateNumber.toString());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        editPay.setText("원");
        editRepairTakeTime.setText("일");

        btnSendEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FinalEstimate finalEstimate = new FinalEstimate();
                final EstimateNum estimateNum = new EstimateNum();
                finalEstimate.forecastWrong = editForecastWrong.getText().toString();
                finalEstimate.pay = editPay.getText().toString();
                finalEstimate.repairTime = editRepairTakeTime.getText().toString();
                finalEstimate.repairDetail = editRepairDetail.getText().toString();
                //finalEstimate.userID = UserUID.getText().toString();
                finalEstimate.estimateNumber = EstimateNumber.toString();

                finalEstimate.setForecastWrong(editForecastWrong.getText().toString());
                finalEstimate.setPay(editPay.getText().toString());
                finalEstimate.setRepairDetail(editRepairDetail.getText().toString());
                finalEstimate.setRepairTime(editRepairTakeTime.getText().toString());
                finalEstimate.setEstimateNumber(EstimateNumber.toString());
                //finalEstimate.setUID(UserUID.getText().toString());

                estimateNum.estimateNum = EstimateNumber.toString();
                estimateNum.setEstimateNum(EstimateNumber.toString());

                FirebaseDatabase.getInstance().getReference().child("users").child(UID.toString()).child("user_Estimate").push().setValue(estimateNum);
                FirebaseDatabase.getInstance().getReference().child("repair_Shop_Estimate").child(stringEstimateNum.toString()).push().setValue(finalEstimate).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SendEstimateActivityForRepairShop.this);
                                builder.setTitle("견적 전송 완료!");
                                builder.setMessage("견적이 유저에게 전송이 완료되었습니다!");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(SendEstimateActivityForRepairShop.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                builder.show();
                            }
                        });
                }
        });

    }

}
