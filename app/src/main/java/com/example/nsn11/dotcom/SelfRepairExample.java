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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class SelfRepairExample extends AppCompatActivity{
    Button btnBack,btnGotoEstimate,btnChoice,btnGotoRepairBoard;
    //TextView textViewInfo;
    LinearLayout layout;
    Toolbar toolbar;
    String activityValue = "";
    TextView textBooting;
    ExpandableListView ListView;
    String trimValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_repair_example);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();

        //btnBack=(Button)findViewById(R.id.btnBack);
        //btnChoice=(Button)findViewById(R.id.btnChoice);
        btnGotoEstimate=(Button)findViewById(R.id.btnGotoEstimate);
        //btnGotoRepairBoard=(Button)findViewById(R.id.btnGotoRepairBoard);
        //textViewInfo=(TextView)findViewById(R.id.textViewInfo);
        //layout = (LinearLayout)findViewById(R.id.layout);
        textBooting = (TextView)findViewById(R.id.textBooting);
        ListView = (ExpandableListView)findViewById(R.id.ListView);

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

        activityValue = getIntent().getExtras().getString("value","");
        //Toast.makeText(getApplicationContext(),activityValue.toString(),Toast.LENGTH_SHORT).show();

        int index = activityValue.toString().indexOf("(");
        trimValue = activityValue.toString().substring(0,index);
        textBooting.setText(trimValue);

        if(activityValue.toString().equals("부팅(전원 켰을 때)")){
            ArrayList<MyGroup> list = new ArrayList<MyGroup>();
            MyGroup myGroup = new MyGroup("부팅 시 블루스크린이 떠요");
            myGroup.child.add("1.시스템 파일 충돌\n"+"시스템 파일 손상으로 인한 증상일 경우\n"+
                    "포멧을 진행해 주시거나 시스템 복원을 시도해\n"+"오류를 해결할 수 있습니다"+"\n\n");
            myGroup.child.add("2.하드디스크 오류\n"+"시스템 파일 손상으로 인한 증상일 경우\n"+
                    "포멧을 진행해 주시거나 시스템 복원을 시도해\n"+"오류를 해결할 수 있습니다"+"\n\n");
            list.add(myGroup);

            myGroup = new MyGroup("삐이익 소리가 반복되면서 부팅이 안돼요");
            myGroup.child.add("1.메모리 카드 이상\n"+"메모리 카드 이상일 경우\n"+
                    "RAM카드를 재장착으로 해결이 가능합니다\n" +
                    "만약 해결이 안될 경우 구글에 컴퓨터 부팅 삐 소리를 검색하시면 소리 횟수에 따른 컴퓨터 진단이 나와있으므로 참고하시기 바랍니다");
            list.add(myGroup);

            myGroup = new MyGroup("부팅 시 검은화면만 나와요");
            myGroup.child.add("1.하드웨어(컴퓨터 부품)문제\n"+"이런 경우 하드디스크를 다시 연결하고 메인보드와도 다시 연결해보는 걸 추천드립니다"+
                    "또한 포멧을 진행해 주시거나 시스템 복원을 시도해\n"+"오류를 해결할 수 있습니다"+"\n\n");
            myGroup.child.add("2.바이러스 감영 경우\n"+"바이러스에 감염된 경우 안전모드로 들어가 시스템 빠른 복원을 시도합니다"+
                    "컴퓨터 부팅 순간부터 F8, F5키를 계속 눌러줍니다\n"+"안전모드 화면에 가면 안전모드를 선택 후 입력창에\n"+""+
                    "C:WWindowsWsystem32WrestoreWrsturi.exe 를 치시는 걸 추천드립니다.");

            list.add(myGroup);

            myGroup = new MyGroup("keyboard not found 라고 나와요");
            myGroup.child.add("1.키보드 연결이 되어있지 않을 때 발생\n"+
                    "키보드를 본체에 연결하고 다시 부팅을 시도해주세요.\n" +
                    "만약 연결하고 부팅해도 다시 저 문구가 나오면 키보드 연결선 문제라고 생각되어 다른 키보드를 연결해주세요");
            list.add(myGroup);

            ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.group_row,R.layout.child_row,list);
            ListView.setIndicatorBounds(width,width-50);
            ListView.setAdapter(adapter);
        }
        else if(activityValue.toString().equals("모니터(화면이 나오지 않는 경우)")){
            ArrayList<MyGroup> list = new ArrayList<MyGroup>();
            MyGroup myGroup = new MyGroup("모니터에 신호없음 이라고 떠요");
            myGroup.child.add("1.모니터 잭 불량\n"+"모니터 잭 불량인 경우\n"+
                    "잭이 내부에서 많이 휘어서 손상이 되었을 수도 있습니다\n"+"잭을 변경해서 끼워보는 걸 추천드립니다"+"\n\n");
            myGroup.child.add("2.그래픽카드 문제\n"+"그래픽카드 문제일 경우\n"+
                    "잭을 그래픽카드에 연결해보고 이래도 안될 경우 그래픽카드 문제이거나 메인보드 문제일 가능성이 있습니다\n");
            list.add(myGroup);

            ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.group_row,R.layout.child_row,list);
            ListView.setIndicatorBounds(width,width-50);
            ListView.setAdapter(adapter);
        }
        else if(activityValue.toString().equals("무한 재부팅(바탕화면이 안 나옴)")){
            ArrayList<MyGroup> list = new ArrayList<MyGroup>();
            MyGroup myGroup = new MyGroup("부팅만 계속 되고 윈도우 화면은 나오지 않아요");
            myGroup.child.add("1.운영체제(윈도우)문제\n"+"운영체제 문제인 경우\n"+
                    "윈도우가 손상을 입은 경우 입니다.\n"+"윈도우를 재설치 하는 방법을 추천 드립니다"+"\n\n");
            myGroup.child.add("2.하드웨어(컴퓨터 부품)문제\n"+"이런 경우 하드디스크를 다시 연결하고 메인보드와도 다시 연결해보는 걸 추천드립니다"+
                    "또한 포멧을 진행해 주시거나 시스템 복원을 시도해 오류를 해결할 수 있습니다"+"\n\n");
            list.add(myGroup);

            ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.group_row,R.layout.child_row,list);
            ListView.setIndicatorBounds(width,width-50);
            ListView.setAdapter(adapter);
        }
        else if(activityValue.toString().equals("인터넷(연결 문제)")){
            ArrayList<MyGroup> list = new ArrayList<MyGroup>();
            MyGroup myGroup = new MyGroup("부팅만 계속 되고 윈도우 화면은 나오지 않아요");
            myGroup.child.add("1.'연결 안됨' 이 뜨는 경우\n"+"'연결 안됨'이 뜨는 경우\n"+
                    "네트워크 문제 해결사를 이용하면 자동으로 해결하는 방법이 있습니다.\n");
            myGroup.child.add("2.어제까지 잘 되다가 갑자기 안되는 경우\n"+"이런 경우는 인터넷을 공급해주는 장치에 문제가 있을 수도 있기에 A/S를 진행해야 합니다");
            list.add(myGroup);

            ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.group_row,R.layout.child_row,list);
            ListView.setIndicatorBounds(width,width-50);
            ListView.setAdapter(adapter);
        }



        /*
        btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeAllViews();
                final Intent intent = new Intent(SelfRepair.this,SelfRepairExample.class);
                final CharSequence[] items = {"부팅(전원 켰을 때)","모니터(화면이 나오지 않는 경우)","재부팅(윈도우 화면이 나오지 않는 경우)","인터넷(연결 문제)"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelfRepair.this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                final String setItem=null;
                //제목
                alertDialogBuilder.setTitle("종류를 선택하세요!");
                alertDialogBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //종류를 선택하는 창
                        btnChoice.setText(items[which]);
                    }
                });
                alertDialogBuilder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(btnChoice.getText().equals("부팅(전원 켰을 때)"))
                        {
                            Toast.makeText(getApplicationContext(),"부팅(전원 켰을 때)선택",Toast.LENGTH_SHORT).show();
                            Button btn[] = new Button[3];
                            btn[0] = new Button(getApplicationContext());
                            btn[0].setText("1.RAM 접촉 불량");
                            btn[0].setWidth(400);
                            btn[0].setTextSize(11);
                            btn[0].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[0]);

                            btn[1] = new Button(getApplicationContext());
                            btn[1].setText("2.하드웨어 접촉 불량");
                            btn[1].setWidth(400);
                            btn[1].setTextSize(11);
                            btn[1].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[1]);

                            btn[2] = new Button(getApplicationContext());
                            btn[2].setText("3.CPU가 신제품일 시 오류");
                            btn[2].setWidth(400);
                            btn[2].setTextSize(11);
                            btn[2].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[2]);

                            btn[0].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //activityValue = "booting_ram";
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[1].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("booting_hardware",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("booting_cpu",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                        }
                        else if(btnChoice.getText().equals("모니터(화면이 나오지 않는 경우)"))
                        {
                            Toast.makeText(getApplicationContext(),"모니터선택",Toast.LENGTH_SHORT).show();
                            Button btn[] = new Button[3];
                            btn[0] = new Button(getApplicationContext());
                            btn[0].setText("1.연결 문제");
                            btn[0].setWidth(400);
                            btn[0].setTextSize(11);
                            btn[0].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[0]);

                            btn[1] = new Button(getApplicationContext());
                            btn[1].setText("2.그래픽카드 문제");
                            btn[1].setWidth(400);
                            btn[1].setTextSize(11);
                            btn[1].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[1]);

                            btn[2] = new Button(getApplicationContext());
                            btn[2].setText("3.RAM 접촉 불량");
                            btn[2].setWidth(400);
                            btn[2].setTextSize(11);
                            btn[2].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[2]);


                            btn[0].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("moniter_connect",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[1].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("moniter_graphic_card",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("moniter_ram_connect",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                        }
                        else if(btnChoice.getText().equals("재부팅(윈도우 화면이 나오지 않는 경우)"))
                        {
                            Toast.makeText(getApplicationContext(),"재부팅선택",Toast.LENGTH_SHORT).show();
                            Button btn[] = new Button[3];
                            btn[0] = new Button(getApplicationContext());
                            btn[0].setText("1.CPU가 신제품일 경우");
                            btn[0].setWidth(400);
                            btn[0].setTextSize(11);
                            btn[0].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[0]);

                            btn[1] = new Button(getApplicationContext());
                            btn[1].setText("2.파워 문제");
                            btn[1].setWidth(400);
                            btn[1].setTextSize(11);
                            btn[1].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[1]);

                            btn[2] = new Button(getApplicationContext());
                            btn[2].setText("3.그래픽카드 사망");
                            btn[2].setWidth(400);
                            btn[2].setTextSize(11);
                            btn[2].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[2]);

                            btn[0].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("reBooting_cpu",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[1].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("reBooting_power",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("reBooting_graphic_card",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });
                        }
                        else if(btnChoice.getText().equals("인터넷(연결 문제)"))
                        {
                            Toast.makeText(getApplicationContext(),"인터넷선택",Toast.LENGTH_SHORT).show();
                            Button btn[] = new Button[3];
                            btn[0] = new Button(getApplicationContext());
                            btn[0].setText("1.랜선 접촉 문제");
                            btn[0].setWidth(400);
                            btn[0].setTextSize(11);
                            btn[0].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[0]);

                            btn[1] = new Button(getApplicationContext());
                            btn[1].setText("2.랜선의 상태 문제");
                            btn[1].setWidth(400);
                            btn[1].setTextSize(11);
                            btn[1].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[1]);

                            btn[2] = new Button(getApplicationContext());
                            btn[2].setText("3.윈도우 환경 설정");
                            btn[2].setWidth(400);
                            btn[2].setTextSize(11);
                            btn[2].setBackgroundColor(Color.parseColor("#ffffff"));
                            layout.addView(btn[2]);

                            btn[0].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("moniter_connect",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[1].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("moniter_graphic_card",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                            btn[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //intent.putExtra("moniter_ram_connect",activityValue.toString());
                                    intent.putExtra("booting_ram",activityValue.toString());
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                });
                alertDialogBuilder.show();
            }
        });
        */
        btnGotoEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfRepairExample.this,EstimateRequest.class);
                startActivity(intent);
                finish();
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
