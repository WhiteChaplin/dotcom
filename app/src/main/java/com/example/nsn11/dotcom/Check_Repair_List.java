package com.example.nsn11.dotcom;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Check_Repair_List extends AppCompatActivity {
    ImageView imageView;
    TextView textUserUID,textExplainText,EstimateNum,UserUID,ExplainText,SeparateText,SelfRepairText,LocationText,MoreOption,textSeparateText,textSelfRepairText,textLocationText,textMoreOption,textEstimateNum,
            textViewForecastWrong,textForecastWrong,textViewRepairWay,textRepairWay,textViewRepairDetail,textRepairDetail,textViewRepairTakeTime,editRepairTakeTime
            ,textViewPay,editPay,RepairShopName,textRepairShopName,textLine,textRequestEstimate,textRepairShopSendEstimate,textState,textViewState;
    Button btnComplete;
    Toolbar toolbar;
    private ArrayList<String> estimateArray = new ArrayList<String>();
    private ArrayList<String> finalEstimateArray = new ArrayList<String>();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference check_State=null;
    String pictureFilePath;
    String estimateNumber;
    String nowState;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_repair_list);
        textUserUID = (TextView)findViewById(R.id.textUserUID);
        textExplainText= (TextView)findViewById(R.id.textExplainText);
        EstimateNum= (TextView)findViewById(R.id.EstimateNum);
        UserUID= (TextView)findViewById(R.id.UserUID);
        ExplainText= (TextView)findViewById(R.id.ExplainText);
        SeparateText= (TextView)findViewById(R.id.SeparateText);
        SelfRepairText= (TextView)findViewById(R.id.SelfRepairText);
        LocationText= (TextView)findViewById(R.id.LocationText);
        MoreOption= (TextView)findViewById(R.id.MoreOption);
        textSeparateText= (TextView)findViewById(R.id.textSeparateText);
        textSelfRepairText= (TextView)findViewById(R.id.textSelfRepairText);
        textLocationText= (TextView)findViewById(R.id.textLocationText);
        textMoreOption= (TextView)findViewById(R.id.textMoreOption);
        textEstimateNum= (TextView)findViewById(R.id.textEstimateNum);
        textForecastWrong= (TextView)findViewById(R.id.textForecastWrong);
        textViewForecastWrong= (TextView)findViewById(R.id.textViewForecastWrong);
        textViewRepairWay= (TextView)findViewById(R.id.textViewRepairWay);
        textRepairWay= (TextView)findViewById(R.id.textRepairWay);
        textRepairDetail= (TextView)findViewById(R.id.textRepairDetail);
        textViewRepairDetail= (TextView)findViewById(R.id.textViewRepairDetail);
        textViewRepairTakeTime= (TextView)findViewById(R.id.textViewRepairTakeTime);
        editRepairTakeTime= (TextView)findViewById(R.id.editRepairTakeTime);
        textViewPay= (TextView)findViewById(R.id.textViewPay);
        editPay= (TextView)findViewById(R.id.editPay);
        textRepairShopName= (TextView)findViewById(R.id.editPay);
        //RepairShopName= (TextView)findViewById(R.id.RepairShopName);
        textLine= (TextView)findViewById(R.id.textLine);
        textRequestEstimate= (TextView)findViewById(R.id.textRequestEstimate);
        textRepairShopSendEstimate= (TextView)findViewById(R.id.textRepairShopSendEstimate);
        textState = (TextView)findViewById(R.id.textState);
        textViewState = (TextView)findViewById(R.id.textViewState);
        btnComplete = (Button)findViewById(R.id.btnComplete);
        imageView = (ImageView)findViewById(R.id.imageView);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent getIntent = getIntent();
        estimateArray = getIntent.getStringArrayListExtra("estimateArray");
        finalEstimateArray = getIntent.getStringArrayListExtra("finalEstimateArray");
        nowState = getIntent.getExtras().getString("nowState","");

        textEstimateNum.setText(finalEstimateArray.get(5).toString());
        pictureFilePath=estimateArray.get(1).toString();
        //Toast.makeText(getApplicationContext(),pictureFilePath.toString(),Toast.LENGTH_SHORT).show();
        Glide.with(getApplicationContext()).
                load(pictureFilePath.toString()).
                into(imageView);
        textUserUID.setText(estimateArray.get(0).toString());
        textExplainText.setText(estimateArray.get(2).toString());
        textSeparateText.setText(estimateArray.get(3).toString());
        textSelfRepairText.setText(estimateArray.get(4).toString());
        textLocationText.setText(estimateArray.get(5).toString());
        textMoreOption.setText(estimateArray.get(6).toString());

        estimateNumber = finalEstimateArray.get(5).toString();

        textForecastWrong.setText(finalEstimateArray.get(0).toString());
        textRepairWay.setText(finalEstimateArray.get(7).toString());
        textRepairDetail.setText(finalEstimateArray.get(2).toString());
        editRepairTakeTime.setText(finalEstimateArray.get(3).toString());
        editPay.setText(finalEstimateArray.get(1).toString());
        textViewState.setText(nowState.toString());

        Toast.makeText(getApplicationContext(),estimateNumber.toString(),Toast.LENGTH_SHORT).show();

        check_State = firebaseDatabase.getReference("user_Estimate").child(estimateNumber);
        check_State.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("nowState").getValue(String.class).equals("수리 완료!")){
                    btnComplete.setClickable(false);
                    btnComplete.setText("수리가 완료되었습니다");
                }
                else if(dataSnapshot.child("nowState").getValue(String.class).equals("수리 평가 완료")){
                    btnComplete.setClickable(false);
                    btnComplete.setText("수리가 완료되었습니다");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("user_Estimate").child(estimateNumber.toString()).child("nowState").setValue("수리 완료!");
                AlertDialog.Builder builder = new AlertDialog.Builder(Check_Repair_List.this);
                builder.setTitle("수리 완료!");
                builder.setMessage("수리가 완료되었습니다. 사용자에게 전송합니다!");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Check_Repair_List.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }
        });
    }

}
