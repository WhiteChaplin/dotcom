package com.example.nsn11.dotcom;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity{
    ImageView imageLogo,imageSlogun,imageSignUp,btn_login;
    Button btnLogin;
    TextView textViewID,textPassword,TextviewSignup,text_first;
    EditText idInput,passwordInput;
    FirebaseRemoteConfig firebaseRemoteConfig;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    String id=null;
    private static final int PERMISSION_CODE=1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        //textViewID = (TextView)findViewById(R.id.textViewID);
        //textPassword = (TextView)findViewById(R.id.textPassword);
        //TextviewSignup = (TextView)findViewById(R.id.TextViewSignUp);
        btn_login = (ImageView) findViewById(R.id.btn_login);
        idInput = (EditText)findViewById(R.id.editID);
        passwordInput = (EditText)findViewById(R.id.editPassword);
        text_first = (TextView)findViewById(R.id.text_first);
        //imageSignUp = (ImageView)findViewById(R.id.imageSignUp);

        final Intent intent = new Intent(this,MainActivity.class);
        final UserInfo userInfo=new UserInfo();

        //권한체크

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET},PERMISSION_CODE);
        }


        /*
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioBtnuser)
                {
                    Toast.makeText(getApplicationContext(),"유저로 로그인",Toast.LENGTH_SHORT).show();
                }
                else if(checkedId == R.id.radioBtnRepair)
                {
                    Toast.makeText(getApplicationContext(),"수리점으로 로그인",Toast.LENGTH_SHORT).show();
                }
            }
        });
        */



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });



        //로그인 인터페이스 리스너
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //로그인이 됬거나 로그아웃 되었을시 호출
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    //로그인
                    startActivity(intent);
                    finish();
                }else
                {
                    //로그아웃
                }
            }
        };


        text_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSignIntent = new Intent(getApplicationContext(),signUp_Activity.class);
                startActivity(goSignIntent);
            }
        });

    }
    void loginEvent()
    {
        if(!idInput.getText().toString().equals("") && !passwordInput.getText().toString().equals("")) {
            firebaseAuth.signInWithEmailAndPassword(idInput.getText().toString(), passwordInput.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //로그인 성공시 여기로 넘어옴
                            if (!task.isSuccessful()) {
                                //로그인 실패시 작동
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getApplicationContext(),"아이디와 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
