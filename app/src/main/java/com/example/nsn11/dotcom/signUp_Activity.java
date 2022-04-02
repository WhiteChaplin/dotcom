package com.example.nsn11.dotcom;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimerTask;

public class signUp_Activity extends AppCompatActivity {
    TextView TextViewDotcom,TextViewSignUp,TextViewEmail,TextViewPass,TextViewName,TextViewNicName,TextViewPhone,TextViewBrith;
    EditText EditEmail,EditTextPass,EditTextName,EditTextNicName,EditTextPhone;
    ImageView imageLogo,btn_create;
    Button btnSign;
    final int DIALOG_DATE=1;
    int m_year=0;
    int m_month=0;
    int m_day=0;
    int NowYear = 0;
    int NowMonth = 0;
    int NowDay = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        //TextViewSignUp= (TextView)findViewById(R.id.TextViewSignUp);
        //TextViewEmail = (TextView)findViewById(R.id.textViewEmail);
        //TextViewPass = (TextView)findViewById(R.id.textViewPass);
        //TextViewName = (TextView)findViewById(R.id.textViewName);
        //TextViewNicName = (TextView)findViewById(R.id.TextViewNicName);
        //TextViewPhone = (TextView)findViewById(R.id.TextViewPhone);

        //imageLogo = (ImageView)findViewById(R.id.imageLogo);

        EditEmail = (EditText)findViewById(R.id.EditEmail);
        EditTextPass= (EditText)findViewById(R.id.EditTextPass);
        EditTextName = (EditText)findViewById(R.id.EditTextName);
        //EditTextNicName= (EditText)findViewById(R.id.EditTextNicName);
        EditTextPhone = (EditText)findViewById(R.id.EditTextPhone);
        btn_create = (ImageView)findViewById(R.id.btn_create);
        //btnSign = (Button)findViewById(R.id.btnSign);

        final String Email = EditEmail.getText().toString();
        final String Name = EditTextName.getText().toString();
        final String Phone = EditTextPhone.getText().toString();
        //final String NicName = EditTextNicName.getText().toString();
        final String Password = EditTextPass.getText().toString();

        Calendar cal = Calendar.getInstance();
        NowYear = cal.get(Calendar.YEAR);
        NowMonth = cal.get(Calendar.MONTH);
        NowDay = cal.get(Calendar.DAY_OF_MONTH);

        final Intent intent = new Intent(signUp_Activity.this,LoginActivity.class);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EditEmail.getText().toString().equals("") && !EditTextPass.getText().toString().equals("") &&!EditTextName.getText().toString().equals("")
                        && !EditTextPhone.getText().toString().equals("")) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(EditEmail.getText().toString(), EditTextPass.getText().toString())
                            .addOnCompleteListener(signUp_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    //회원가입 완료 시 여기코드로 넘어옴

                                    AlertDialog.Builder builder = new AlertDialog.Builder(signUp_Activity.this);
                                    builder.setTitle("회원가입 완료!");
                                    builder.setMessage("회원가입이 완료 되었습니다! 확인 버튼을 누르면 자동으로 로그인이 됩니다.");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            UserInfo userInfo = new UserInfo();
                                            userInfo.userEmail = Email;
                                            userInfo.userName = Name;
                                            //userInfo.userNicName = NicName;
                                            userInfo.userPhone = Phone;

                                            userInfo.setUserName(EditTextName.getText().toString());
                                            userInfo.setUserEmail(EditEmail.getText().toString());
                                            userInfo.setUserPhone(EditTextPhone.getText().toString());
                                            //userInfo.setUserNicName(EditTextNicName.getText().toString());

                                            String uid = task.getResult().getUser().getUid();
                                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userInfo);

                                            Intent intent = new Intent(signUp_Activity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.show();

                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"빈칸을 채워주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
