package com.example.nsn11.dotcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfoActivity extends AppCompatActivity{
    ImageView back;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String userName;
    String userTel;
    String uID;
    String userNicName;
    TextView textEmail,textNicName,textTel,textViewID,textViewNicName,textName,textViewTel,textViewUserUID,textUserUID,textChangePassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_activity);
        textEmail = (TextView)findViewById(R.id.textEmail);
        textNicName = (TextView)findViewById(R.id.textNicName);
        textTel = (TextView)findViewById(R.id.textTel);
        textViewID = (TextView)findViewById(R.id.textViewID);
        textViewNicName = (TextView)findViewById(R.id.textViewNicName);
        textViewTel = (TextView)findViewById(R.id.textViewTel);
        textViewUserUID= (TextView)findViewById(R.id.textViewUserUID);
        textUserUID= (TextView)findViewById(R.id.textUserUID);
        textName = (TextView)findViewById(R.id.textName);
        textChangePassword = (TextView)findViewById(R.id.textChangePassword);
        back = (ImageView)findViewById(R.id.btn_back);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        textEmail.setText(firebaseUser.getEmail());
        myRef = firebaseDatabase.getReference("users");
        uID = firebaseUser.getUid();
        myRef.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("userName").getValue(String.class);
                userTel = dataSnapshot.child("userPhone").getValue(String.class);
                userNicName = dataSnapshot.child("userNicName").getValue(String.class);
                textName.setText(userName);
                textTel.setText(userTel);
                textNicName.setText(userNicName);
                textUserUID.setText(uID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
                builder.setTitle("비밀번호 재설정");
                builder.setMessage("이메일로 비밀번호 재설정 메일을 전송했습니다! 이메일을 확인해주세요");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
}
