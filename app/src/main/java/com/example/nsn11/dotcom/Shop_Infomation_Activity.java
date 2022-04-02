package com.example.nsn11.dotcom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class Shop_Infomation_Activity extends Activity implements OnMapReadyCallback {
    TextView textBest,textShopName,textShopLocation,textRepairCount,textCustomerRepute,textCustomerReview,textCount,
            textReview,textShopTime,textWeekday,textWeekdayTime,textSaturday,textSaturdayTime,textRepute,
            textSunday,textSundayTime,textPersonalDay,textPersonalDayWhen,textLocation;
    ImageView imgShopInfo;
    RelativeLayout Layout,LayoutNaverMap;
    Button btnFinish,btnShopInfomation;
    TMapView tMapView = null;
    TMapMarkerItem item=null;
    String ShopName = "";
    String shopID;
    DatabaseReference get_Shop_Infomation=null;
    DatabaseReference get_Shop_Name=null;
    DatabaseReference get_Shop_repairList=null;
    DatabaseReference get_Shop_repair_Repute_Rating=null;
    DatabaseReference get_Shop_repair_Repute_count=null;
    String repute_Rating;
    ArrayList <Double> repute_sum = new ArrayList<Double>();
    double repute_double;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    long repair_Count=0;
    MapView mapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int PERMISSION_CODE=1;
    private FusedLocationSource locationSource;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shop_infomation_activity);


        textBest = (TextView)findViewById(R.id.textBest);
        textShopName = (TextView)findViewById(R.id.textShopName);
        textShopLocation = (TextView)findViewById(R.id.textShopLocation);
        textRepairCount = (TextView)findViewById(R.id.textRepairCount);
        textCustomerRepute = (TextView)findViewById(R.id.textCustomerRepute);
        textCustomerReview = (TextView)findViewById(R.id.textCustomerReview);
        textCount = (TextView)findViewById(R.id.textCount);
        textReview = (TextView)findViewById(R.id.textReview);
        //textShopExplain = (TextView)findViewById(R.id.textShopExplain);
        //textShopService = (TextView)findViewById(R.id.textShopService);
        textShopTime = (TextView)findViewById(R.id.textShopTime);
        textWeekday = (TextView)findViewById(R.id.textWeekday);
        textWeekdayTime = (TextView)findViewById(R.id.textWeekdayTime);
        textSaturday = (TextView)findViewById(R.id.textSaturday);
        textSaturdayTime = (TextView)findViewById(R.id.textSaturdayTime);
        textSunday = (TextView)findViewById(R.id.textSunday);
        textSundayTime = (TextView)findViewById(R.id.textSundayTime);
        textRepute = (TextView)findViewById(R.id.textRepute);
        //textPersonalDay = (TextView)findViewById(R.id.textPersonalDay);
        //textPersonalDayWhen = (TextView)findViewById(R.id.textPersonalDayWhen);
        //textLocation = (TextView)findViewById(R.id.textLocation);
        imgShopInfo = (ImageView)findViewById(R.id.imgShopInfo);
        //Layout = (RelativeLayout)findViewById(R.id.Layout);
        //mapView = (MapView) findViewById(R.id.map_view);
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnShopInfomation = (Button)findViewById(R.id.btnShopInfomation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        Intent getIntent = getIntent();
        shopID = getIntent.getExtras().getString("shopID");
        //Toast.makeText(getApplicationContext(),shopID.toString(),Toast.LENGTH_SHORT).show();
        textShopLocation.setText("");
        textBest.setText("");

        get_Shop_Infomation = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString()).child("text_Infomation_List");
        get_Shop_Infomation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Runnable runnable = new Runnable();
                //Thread t = new Thread(runnable);
                //t.start();
                textWeekdayTime.setText(dataSnapshot.child("repairShopWorkTimeWeekday").getValue(String.class));
                textSaturdayTime.setText(dataSnapshot.child("repairShopWorkTimeSaturday").getValue(String.class));
                textSundayTime.setText(dataSnapshot.child("repairShopWorkTimeSunday").getValue(String.class));

                //t.interrupt();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        get_Shop_Name = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString());
        get_Shop_Name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textShopName.setText(dataSnapshot.child("shopName").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        get_Shop_repair_Repute_Rating = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString()).child("shop_Repute");
        get_Shop_repair_Repute_Rating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String repute;
                    if(dataSnapshot1.child("repute_Rating").getValue(String.class).equals("")){
                        return;
                    }
                    else{
                        repute = dataSnapshot1.child("repute_Rating").getValue(String.class);
                        //Toast.makeText(getApplicationContext(),repute.toString(),Toast.LENGTH_SHORT).show();
                        Double repute_double = Double.valueOf(repute);
                        repute_sum.add(repute_double);
                        //Toast.makeText(getApplicationContext(),repute_double.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                Double sum = 0.0;
                Double average;
                for(int i=0;i<repute_sum.size();i++){
                    sum +=repute_sum.get(i);
                }
                average = sum/repute_sum.size();
                String ave = String.format("%.2f",average);
                Toast.makeText(getApplicationContext(),ave,Toast.LENGTH_SHORT).show();
                if(ave.equals("NaN")){
                    textRepute.setText("0점");
                }
                else{
                    textRepute.setText(ave.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        get_Shop_repair_Repute_count = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString()).child("shop_Repute");
        get_Shop_repair_Repute_count.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textReview.setText(String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        get_Shop_repairList = firebaseDatabase.getReference("shop_Infomation").child(shopID.toString()).child("vision_My_Repair_Estimate_Number");
        get_Shop_repairList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                repair_Count = dataSnapshot.getChildrenCount();
                    textCount.setText(String.valueOf(repair_Count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnShopInfomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop_Infomation_Activity.this,RepairShopInfomationDetailActivity.class);
                intent.putExtra("shopID",shopID.toString());
                startActivity(intent);
            }
        });

        //NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("07x0q8dwaw"));
        //LayoutNaverMap = (MapView)findViewById(R.id.map_view);
        //mapView.getMapAsync(this);


        //locationSource = new FusedLocationSource(this,LOCATION_PERMISSION_REQUEST_CODE);

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    /*
    class Runnable implements java.lang.Runnable{

        Runnable(){

        }

        @Override
        public void run() {
            try{
                ProgressDialog dialog = ProgressDialog.show(Shop_Infomation_Activity.this,"","");
                dialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    */
}
