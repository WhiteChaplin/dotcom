package com.example.nsn11.dotcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class get_Estimate_Activity extends AppCompatActivity{
    ImageView imageView,back;
    TextView textUserUID,textExplainText,EstimateNum,UserID,ExplainText,SeparateText,SelfRepairText,LocationText,MoreOption,
            textSeparateText,textSelfRepairText,textLocationText,textMoreOption,textEstimateNum,Date,textDate,
            textViewTitle,textTitle;
    Button btnWriteEstimate;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference user_estimateRef = null;
    DatabaseReference estimate_numRef=null;
    FirebaseStorage firebaseStorage=null;
    StorageReference storageReference=null;
    StorageReference pathReference=null;
    String UID=null;
    String Estimate_UID=null;
    private String userUID;
    private String pictureFilePath;
    private String explainEditText;
    private String separateText;
    private String selfRepairText;
    private String locationText;
    private String moreOption;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_user_estimate);

        imageView = (ImageView)findViewById(R.id.imageView);
        textExplainText = (TextView)findViewById(R.id.textExplainText);
        textLocationText = (TextView)findViewById(R.id.textLocationText);
        textMoreOption= (TextView)findViewById(R.id.textMoreOption);
        textSelfRepairText = (TextView)findViewById(R.id.textSelfRepairText);
        textSeparateText = (TextView)findViewById(R.id.textSeparateText);
        textUserUID = (TextView)findViewById(R.id.textUserUID);
        textEstimateNum = (TextView)findViewById(R.id.textEstimateNum);
        EstimateNum = (TextView)findViewById(R.id.EstimateNum);
        UserID = (TextView)findViewById(R.id.UserUID);
        ExplainText = (TextView)findViewById(R.id.ExplainText);
        SeparateText= (TextView)findViewById(R.id.SeparateText);
        SelfRepairText= (TextView)findViewById(R.id.SelfRepairText);
        LocationText= (TextView)findViewById(R.id.LocationText);
        MoreOption= (TextView)findViewById(R.id.MoreOption);
        Date = (TextView)findViewById(R.id.Date);
        textDate = (TextView)findViewById(R.id.textDate);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textTitle = (TextView)findViewById(R.id.textTitle);
        btnWriteEstimate = (Button)findViewById(R.id.btnWriteEstimate);
        back = (ImageView)findViewById(R.id.btn_back);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        estimate_numRef = firebaseDatabase.getReference("Estimate_Num");
        UID = firebaseUser.getUid();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        estimate_numRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Estimate_UID = snapshot.child("estimateNum").getValue(String.class);
                    textEstimateNum.setText(Estimate_UID.toString());
                }
                user_estimateRef = firebaseDatabase.getReference("user_Estimate");

                //user_estimateRef.child(Estimate_UID).addValueEventListener(new ValueEventListener()
                user_estimateRef.child(Estimate_UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            textUserUID.setText(dataSnapshot.child("userUID").getValue(String.class));
                            pictureFilePath = dataSnapshot.child("pictureFilePath").getValue(String.class);
                            textExplainText.setText(dataSnapshot.child("explainEditText").getValue(String.class));
                            textSeparateText.setText(dataSnapshot.child("separateText").getValue(String.class));
                            textSelfRepairText.setText(dataSnapshot.child("selfRepairText").getValue(String.class));
                            textLocationText.setText(dataSnapshot.child("locationText").getValue(String.class));
                            textMoreOption.setText(dataSnapshot.child("moreOption").getValue(String.class));
                            textDate.setText(dataSnapshot.child("date").getValue(String.class));
                            //storageReference = firebaseStorage.getReference().child("images").child("201906012318.png");
                            //storageReference = firebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/dotcom-f8c8a.appspot.com/o/images%2F201906012318.png?alt=media&token=9714e700-de16-49ac-a763-16826fb8f496");
                            Glide.with(getApplicationContext())
                                .load(pictureFilePath)
                                .into(imageView);
                            textTitle.setText(dataSnapshot.child("editTitle").getValue(String.class));
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

        btnWriteEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(get_Estimate_Activity.this,SendEstimateActivityForRepairShop2.class);
                intent.putExtra("UserUID",textUserUID.getText().toString());
                intent.putExtra("EstimateNum",textEstimateNum.getText().toString());
                intent.putExtra("repairShopName",textLocationText.getText().toString());
                intent.putExtra("date",textDate.getText().toString());
                intent.putExtra("title",textTitle.getText().toString());
                startActivity(intent);
            }
        });
    }

}
