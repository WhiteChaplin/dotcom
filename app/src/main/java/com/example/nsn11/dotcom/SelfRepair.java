package com.example.nsn11.dotcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class SelfRepair extends AppCompatActivity{
    Button btnBack,btnGotoEstimate,btnChoice,btnGotoRepairBoard;
    ImageView toolbarImage,imageActivity;
    //TextView textViewInfo;
    TextView imageSkip;
    LinearLayout layout;
    Toolbar toolbar;
    TextView text_insetkeyword;
    ExpandableListView ListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_repair);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();

        text_insetkeyword = (TextView)findViewById(R.id.text_insetkeyword);
        toolbarImage = (ImageView)findViewById(R.id.toolbarImage);
        imageActivity = (ImageView)findViewById(R.id.btn_search);
        imageSkip = (TextView)findViewById(R.id.btn_skip);

        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.whitelist);
        //getSupportActionBar().setTitle("DotCom");
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        //toolbar = (Toolbar)findViewById(R.id.Toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_insetkeyword.getText().toString().equals("키워드를 입력해주세요")){
                    Toast.makeText(getApplicationContext(),"키워드를 선택해주세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SelfRepair.this,SelfRepairExample.class);
                    intent.putExtra("value",text_insetkeyword.getText().toString());
                    startActivity(intent);
                }
            }
        });

        imageSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfRepair.this,EstimateRequest.class);
                startActivity(intent);
                finish();
            }
        });

        text_insetkeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"부팅(전원 켰을 때)","모니터(화면이 나오지 않는 경우)","무한 재부팅(바탕화면이 안 나옴)","인터넷(연결 문제)"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelfRepair.this);

                //제목
                alertDialogBuilder.setTitle("터치해서 종류를 선택하세요!");
                alertDialogBuilder.setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex[0] = which;
                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //textChoice.setText(items[which]);
                        text_insetkeyword.setText(items[selectedIndex[0]]);
                    }
                });
                alertDialogBuilder.show();
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
