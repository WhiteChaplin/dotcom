package com.example.nsn11.dotcom;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String id=null;
    TextView text;
    Toolbar toolbar;
    Button btnGotoSelfRepair,btnGotoRepairCase;
    ImageView btnMenu,btnAlarm;
    ViewFlipper vFlipper;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView textLogo;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference myRef = null;
    ChildEventListener mchild;
    String userName=null;
    String userTel = null;
    String uID=null;
    String thread_userName;
    String thread_userEmail;
    String get_thread_userName;
    String get_thread_userEmail;
    List<UserInfo> userInfoList = new ArrayList<>();
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    ProgressDialog progressDialog;
    private Handler handler = new Handler();
    final private int PROGRESS_DIALOG = 0;


    //FragmentManager fragmentManager = null;
    //FragmentTransaction fragmentTransaction=null;
    //TmapService tmapService=null;
    //public static final int PERMISSION_CODE = 1;
    //Button intentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCreateDialog(PROGRESS_DIALOG);
        Thread thread= new Thread(null,getDATA);
        thread.start();
        //showDialog(PROGRESS_DIALOG);
        //onCreateDialog(PROGRESS_DIALOG);

        //위젯 등록
        text=(TextView)findViewById(R.id.text);
        //btnGotoSelfRepair=(Button)findViewById(R.id.btnGotoSelfRepair);
        //btnGotoRepairCase=(Button)findViewById(R.id.btnGotoRepairCase);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        navigationView=(NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        btnMenu = (ImageView) findViewById(R.id.btnMenu);
        btnAlarm = (ImageView) findViewById(R.id.btnAlarm);
        vFlipper = (ViewFlipper)findViewById(R.id.viewFlipper1);
        textLogo = (TextView)findViewById(R.id.textLogo);

        textLogo.bringToFront();
        btnAlarm.bringToFront();
        btnMenu.bringToFront();

        /*
        //네비게이션 관련 설정
        View nav_header_view =  navigationView.getHeaderView(0);
        final TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.textView);
        final TextView nav_header_id_text2 = (TextView) nav_header_view.findViewById(R.id.textView2);
         */

        //Intent 관련 설정
        final Intent intent = new Intent(this,TmapSearchNearRepairShop.class);
        final Intent estimateIntent = new Intent(this,EstimateRequest.class);
        //final Intent intentUserInfo = new Intent(this,UserInfoActivity.class);
        final Intent intentUserInfo = new Intent(this,UserInfoActivity.class);

        //툴바 관련 설정
        /*
        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.whitelist);
        getSupportActionBar().setTitle("DotCom");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        */

        //Firebase 관련 설정
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("users");
        uID = firebaseUser.getUid();


        //nav_header_id_text.setText(get_thread_userEmail);
        //nav_header_id_text2.setText(get_thread_userName);
        //Toast.makeText(getApplicationContext(),uID,Toast.LENGTH_SHORT).show();


        //Toast.makeText(getApplicationContext(),"안녕하세요 "+firebaseUser.getEmail()+"님!",Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),firebaseUser.getUid(),Toast.LENGTH_SHORT).show();
        //UserInfo userInfo = new UserInfo();
        //nav_header_id_text.setText(firebaseUser.getEmail().toString());
        //nav_header_id_text2.setText(userName);



        vFlipper.setDisplayedChild(R.id.main1);
        vFlipper.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            @Override
            public void onSwipeRight() {
                vFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_right_in));
                vFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_right_out));
                vFlipper.showPrevious();
            }

            @Override
            public void onSwipeLeft() {
                vFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_left_in));
                vFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_left_out));

                vFlipper.showNext();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,UserCheckEstimateActivity.class);
                intent1.putExtra("userName",userName.toString());
                startActivity(intent1);
            }
        });


        /*
        btnGotoSelfRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSelfRepair = new Intent(MainActivity.this,SelfRepair.class);
                startActivity(gotoSelfRepair);
            }
        });

        btnGotoRepairCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,SetShopActivity.class);
                intent1.putExtra("uID",uID);
                startActivity(intent1);
                Toast.makeText(getApplicationContext(),"아직 완성하지 못했어요 ㅠㅠ",Toast.LENGTH_SHORT).show();
            }
        });
        */

        //Firebase 관련 코드

        //로그인 안되었을 시
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }



        //User 데이터 가져오는 코드!
        /*
       myRef.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   userName = dataSnapshot.child("userName").getValue(String.class);
                   //userTel = dataSnapshot.child("userPhone").getValue(String.class);
                   //Toast.makeText(getApplicationContext(),userName+ " "+userTel,Toast.LENGTH_LONG).show();
                   nav_header_id_text.setText(firebaseUser.getEmail().toString());
                   nav_header_id_text2.setText(userName);
                   Toast.makeText(getApplicationContext(),"안녕하세요 "+userName+"님!",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
         */

        myRef.child(uID).child("user_Estimate").child("user_Estimate_Number").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //NotificationActivity notificationActivity = new NotificationActivity(getApplicationContext());
                //notificationActivity.alarmNotification();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //네비게이션 뷰 관련 코드
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id)
                {
                    case R.id.itemMyInfo:
                        //Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        startActivity(intentUserInfo);
                        break;
                    case R.id.itemAppInfomation:
                        //Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        if(userName.toString().contains("수리점")){
                            Intent ImageIntent = new Intent(MainActivity.this,get_Estimate_Activity.class);
                            startActivity(ImageIntent);
                        }
                        else{

                        }
                        break;
                    case R.id.itemCustomerCenter:
                        //Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        //Intent intent1 = new Intent(MainActivity.this,TextButtonDisable.class);
                        //startActivity(intent1);
                        Intent intent1 = new Intent(MainActivity.this,TestUploadVideo.class);
                        startActivity(intent1);
                        break;
                    case R.id.itemDotcomGotoReview:
                        if(userName.toString().contains("수리점")){
                            Intent gotoRepairList = new Intent(MainActivity.this,RepairShopCheckRepairList.class);
                            startActivity(gotoRepairList);
                        }
                        else{

                        }
                        //Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.itemFindRepairShop:
                        //Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        //Intent gotoNavermap = new Intent(MainActivity.this,NaverMapFindNearRepairShop.class);
                        //startActivity(gotoNavermap);
                        Intent intent2 = new Intent(MainActivity.this,get_Video_Activity.class);
                        startActivity(intent2);
                        break;
                    case R.id.itemRepairCase:
                        //Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        //Intent intentEstimateUser = new Intent(MainActivity.this,UserCheckEstimateActivity.class);
                        //intentEstimateUser.putExtra("userName",userName.toString());
                        //startActivity(intentEstimateUser);
                        break;
                    case R.id.itemWantPartner:
                        //Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                        //Intent intentAddshop = new Intent(MainActivity.this,AddShopActivity.class);
                        //startActivity(intentAddshop);
                        Intent gotoMyRepairState = new Intent(MainActivity.this,CheckMyRepairNowStateActivity.class);
                        startActivity(gotoMyRepairState);
                        break;
                    case R.id.itemLogout:
                        Toast.makeText(getApplicationContext(),menuItem.getTitle()+"되었습니다",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        Intent LoginIntent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(LoginIntent);
                }
                return true;
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.alarm:
                Toast.makeText(MainActivity.this,"알람버튼 클릭",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;

            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
            {
                super.onBackPressed();
                finish();;
            }
            else
            {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누르면 앱이 종료 됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            return false;
        }
        switch (vFlipper.getDisplayedChild()) {
            case 0:
                //Toast.makeText(getApplicationContext(), "빨강", Toast.LENGTH_SHORT).show();
                Intent goMatching = new Intent(MainActivity.this,SelfRepair.class);
                startActivity(goMatching);
                break;
            case 1:
                //Toast.makeText(getApplicationContext(), "아직 완성하지 못했어요 ㅠㅠ", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(MainActivity.this,SetShopActivity.class);
                //intent.putExtra("uID",uID.toString());
                //startActivity(intent);
                break;
            case 2:
                //Toast.makeText(getApplicationContext(), "파랑", Toast.LENGTH_SHORT).show();
                Intent goFindNearRepairShop = new Intent(MainActivity.this,NaverMapFindNearRepairShop.class);
                startActivity(goFindNearRepairShop);
                break;
            default:
                vFlipper.setDisplayedChild(R.id.main1);
                break;
        }
        return false;
    }

    private Runnable getDATA= new Runnable() {
        public void run() {
            try {
                //원하는 자료 처리(데이터 로딩등)
                String uID = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase firebaseDatabase = null;
                DatabaseReference myRef = null;
                firebaseDatabase = FirebaseDatabase.getInstance();
                myRef = firebaseDatabase.getReference("users");
                myRef.child(uID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userName = dataSnapshot.child("userName").getValue(String.class);
                        //userTel = dataSnapshot.child("userPhone").getValue(String.class);
                        // Toast.makeText(getApplicationContext(),userName+ " "+userTel,Toast.LENGTH_LONG).show();
                         thread_userEmail = firebaseUser.getEmail().toString();
                         thread_userName = userName;
                         get_thread_userEmail = thread_userEmail;
                         get_thread_userName = thread_userName;
                        if(userName.contains("수리점")){
                            navigationView.getMenu().clear();
                            navigationView.inflateMenu(R.menu.main_navigation_meue_repair_shop);
                        }
                        else{
                            navigationView.getMenu().clear();
                            navigationView.inflateMenu(R.menu.main_navigation_menu);
                        }
                        //네비게이션 관련 설정
                        View nav_header_view =  navigationView.getHeaderView(0);
                        final TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.textView);
                        final TextView nav_header_id_text2 = (TextView) nav_header_view.findViewById(R.id.textView2);
                        nav_header_id_text.setText(get_thread_userEmail);
                        nav_header_id_text2.setText(get_thread_userName);
                        Toast.makeText(getApplicationContext(),"안녕하세요 "+userName+"님!",Toast.LENGTH_SHORT).show();
                        handler.post(updateResults); //처리 완료후 Handler의 Post를 사용해서 이벤트 던짐
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                } catch (Exception e) {
                    Log.e("getDATA", e.toString());
                }
            }
        };

    private Runnable updateResults = new Runnable() {
        public void run () {
            progressDialog.dismiss();
            removeDialog(PROGRESS_DIALOG);
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case (PROGRESS_DIALOG):
                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("잠시만 기다려주세요");
                return progressDialog;
        }
        return null;
    }



    @Override
    protected void onStart() {
        Thread thread;
        thread = new Thread(null,getDATA);
        thread.start();
        showDialog(PROGRESS_DIALOG);
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Thread thread;
        thread = new Thread(null,getDATA);
        thread.start();
        showDialog(PROGRESS_DIALOG);
        super.onRestart();
    }
}
