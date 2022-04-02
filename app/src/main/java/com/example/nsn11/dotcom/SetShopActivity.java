package com.example.nsn11.dotcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SetShopActivity extends AppCompatActivity {
    TextView textShopName,textShopLatitude,textShopLongitude,textShopCity,textShopID,explainRepairShop,repairShopService,workTime,workTimeSaturday,workTimeSunday,textViewTel,textViewKakao;
    EditText editShopName,editShopLatitude,editShopLongitude,editShopCity,editExplainRepairShop,editRepairShopService,editWorkTimeWeekday,editWorkTimeSaturday,editWorkTimeSunday,editTel,editKakao;
    Button btnSet,btnNowState;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String UID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_shop);

        String intent = getIntent().getExtras().getString("uID");


        textShopName = (TextView)findViewById(R.id.textShopName);
        textShopLatitude = (TextView)findViewById(R.id.textShopLatitude);
        textShopLongitude = (TextView)findViewById(R.id.textShopLongitude);
        textShopCity = (TextView)findViewById(R.id.textShopCity);
        textShopID = (TextView)findViewById(R.id.textShopID);
        textViewTel = (TextView)findViewById(R.id.textViewTel);
        textViewKakao = (TextView)findViewById(R.id.textViewKakao);
        explainRepairShop = (TextView)findViewById(R.id.explainRepairShop);
        repairShopService= (TextView)findViewById(R.id.repairShopService);
        workTime= (TextView)findViewById(R.id.workTime);
        workTimeSaturday= (TextView)findViewById(R.id.workTimeSaturday);
        workTimeSunday= (TextView)findViewById(R.id.workTimeSunday);
        editShopName = (EditText)findViewById(R.id.editShopName);
        editShopLatitude = (EditText)findViewById(R.id.editShopLatitude);
        editShopLongitude = (EditText)findViewById(R.id.editShopLongitude);
        editShopCity = (EditText)findViewById(R.id.editShopCity);
        editExplainRepairShop = (EditText)findViewById(R.id.editExplainRepairShop);
        editRepairShopService = (EditText)findViewById(R.id.editRepairShopService);
        editWorkTimeWeekday = (EditText)findViewById(R.id.editWorkTimeWeekday);
        editWorkTimeSaturday = (EditText)findViewById(R.id.editWorkTimeSaturday);
        editWorkTimeSunday = (EditText)findViewById(R.id.editWorkTimeSunday);
        editTel = (EditText)findViewById(R.id.editTel);
        editKakao = (EditText)findViewById(R.id.editKakao);

        btnSet = (Button)findViewById(R.id.btnSet);
        btnNowState = (Button)findViewById(R.id.btnNowState);

        textShopID.setText(intent.toString());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        UID = firebaseAuth.getUid();

        textShopID.setText(UID.toString());

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editShopName.getText().toString().equals("") && !editShopLatitude.getText().toString().equals("") &&
                        !editShopLongitude.getText().toString().equals("") && !editShopCity.getText().toString().equals("")){
                    AddShop addShop = new AddShop();
                    addShop.shopName = editShopName.getText().toString();
                    addShop.shopLatitude = editShopLatitude.getText().toString();
                    addShop.shopLongitude = editShopLongitude.getText().toString();
                    addShop.shopCity = editShopCity.getText().toString();
                    addShop.userID = textShopID.getText().toString();


                    addShop.setShopName(editShopName.getText().toString());
                    addShop.setShopLatitude(editShopLatitude.getText().toString());
                    addShop.setShopLongitude(editShopLongitude.getText().toString());
                    addShop.setShopCity(editShopCity.getText().toString());
                    addShop.setUserID(textShopID.getText().toString());


                    FirebaseDatabase.getInstance().getReference().child("shop_Infomation").child(UID.toString()).setValue(addShop);

                    Toast.makeText(getApplicationContext(),"등록 완료!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNowState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editExplainRepairShop.getText().toString().equals("") && !editRepairShopService.getText().toString().equals("") && !editWorkTimeSaturday.getText().toString().equals("")
                        && !editWorkTimeSunday.getText().toString().equals("") && !editWorkTimeWeekday.getText().toString().equals("")){
                    RepairShopInfomation repairShopInfomation = new RepairShopInfomation();
                    repairShopInfomation.repairShopExplain = editExplainRepairShop.getText().toString();
                    repairShopInfomation.repairShopService = editRepairShopService.getText().toString();
                    repairShopInfomation.repairShopWorkTimeWeekday = editWorkTimeWeekday.getText().toString();
                    repairShopInfomation.repairShopWorkTimeSaturday = editWorkTimeSaturday.getText().toString();
                    repairShopInfomation.repairShopWorkTimeSunday = editWorkTimeSunday.getText().toString();
                    repairShopInfomation.shopTel = editTel.getText().toString();
                    repairShopInfomation.shopKakao = editKakao.getText().toString();

                    repairShopInfomation.setRepairShopExplain(editExplainRepairShop.getText().toString());
                    repairShopInfomation.setRepairShopService(editRepairShopService.getText().toString());
                    repairShopInfomation.setRepairShopWorkTimeWeekday(editWorkTimeWeekday.getText().toString());
                    repairShopInfomation.setRepairShopWorkTimeSaturday(editWorkTimeSaturday.getText().toString());
                    repairShopInfomation.setRepairShopWorkTimeSunday(editWorkTimeSunday.getText().toString());
                    repairShopInfomation.setShopTel(editTel.getText().toString());
                    repairShopInfomation.setShopKakao(editKakao.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("shop_Infomation").child(UID.toString()).child("text_Infomation_List").setValue(repairShopInfomation);

                    Toast.makeText(getApplicationContext(),"등록 완료!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
