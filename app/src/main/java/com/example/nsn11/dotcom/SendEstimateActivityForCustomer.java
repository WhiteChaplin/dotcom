package com.example.nsn11.dotcom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.HashMap;

public class SendEstimateActivityForCustomer extends AppCompatActivity implements OnMapReadyCallback{
    TextView textViewForecastWrong,textForecastWrong,textViewRepairWay,textRepairWay,textViewRepairDetail,textRepairDetail,textViewRepairTakeTime,editRepairTakeTime
            ,textViewPay,editPay,RepairShopName,textRepairShopName,EstimateNum,textEstimateNum,textTitle,textSeparate;
    Button btnSelectShop,btnCall,btnChat,btnRepairShopInfo;
    MapView estimate_NaverMap;
    ImageView imageView,back;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int PERMISSION_CODE=1;
    private FusedLocationSource locationSource;
    public LocationManager locationManager=null;
    public LocationListener locationListener=null;
    double longitude=0.0;
    double latitude=0.0;
    DatabaseReference repair_Shop_add_List=null;
    String get_Repair_Shop_Estimate_Num=null;
    DatabaseReference get_Repiar_Shop_Location_Ref=null;
    DatabaseReference get_shop_UID=null;
    DatabaseReference check_matching=null;
    ArrayList<String> estimateArray = new ArrayList<String>();
    String key;
    String UID;
    String Estimate_Num;
    String trim_Repair_Shop_Name;
    String str_shop_latitude;
    String str_shop_longitude;
    double dou_shop_latitude;
    double dou_shop_longitude;
    String separate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_estimate_activity_for_customer);
        //textViewForecastWrong = (TextView)findViewById(R.id.textViewForecastWrong);
        textForecastWrong= (TextView)findViewById(R.id.text_statecontent);
        //textViewPay= (TextView)findViewById(R.id.textViewPay);
        editPay= (TextView)findViewById(R.id.text_moneycontent);
        //textViewRepairWay= (TextView)findViewById(R.id.textViewRepairWay);
        textRepairWay= (TextView)findViewById(R.id.text_solutioncontent);
        //textViewRepairDetail= (TextView)findViewById(R.id.textViewRepairDetail);
        textRepairDetail= (TextView)findViewById(R.id.text_repairDetailContent);
        //textViewRepairTakeTime= (TextView)findViewById(R.id.textViewRepairTakeTime);
        editRepairTakeTime= (TextView)findViewById(R.id.text_date);
        //EstimateNum = (TextView)findViewById(R.id.EstimateNum);
        //textEstimateNum = (TextView)findViewById(R.id.textEstimateNum);
        //RepairShopName = (TextView)findViewById(R.id.text_repairshop);

        textRepairShopName = (TextView)findViewById(R.id.text_repairshop);
        btnSelectShop = (Button)findViewById(R.id.btn_select);
        btnCall = (Button)findViewById(R.id.btn_call);
        btnChat = (Button)findViewById(R.id.btn_chat);
        btnRepairShopInfo = (Button)findViewById(R.id.btn_repairshopinfo);
        textTitle = (TextView)findViewById(R.id.text_estimatename);
        textSeparate = (TextView)findViewById(R.id.text_estimatedevice);
        back = (ImageView)findViewById(R.id.btn_back);



        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("07x0q8dwaw"));
        estimate_NaverMap = (MapView)findViewById(R.id.estimate_NaverMap);
        estimate_NaverMap.getMapAsync(this);

        locationSource = new FusedLocationSource(this,LOCATION_PERMISSION_REQUEST_CODE);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        UID = firebaseUser.getUid().toString();

        Intent getIntent = getIntent();

        estimateArray = getIntent.getStringArrayListExtra("Array");

        textForecastWrong.setText(estimateArray.get(0).toString());
        editPay.setText(estimateArray.get(1));
        textRepairWay.setText(estimateArray.get(2));
        textRepairDetail.setText(estimateArray.get(3));
        editRepairTakeTime.setText(estimateArray.get(4));
        textTitle.setText(estimateArray.get(8));
        //textEstimateNum.setText(estimateArray.get(6));
        textRepairShopName.setText(estimateArray.get(7));
        Estimate_Num = estimateArray.get(6).toString();
        //Estimate_Num = textEstimateNum.getText().toString();
        //EstimateNum.setVisibility(View.INVISIBLE);
        //textEstimateNum.setVisibility(View.INVISIBLE);
        int index = textRepairShopName.getText().toString().indexOf("/");
        trim_Repair_Shop_Name = textRepairShopName.getText().toString().substring(0,index).toString();
        //Toast.makeText(getApplicationContext(),trim_Repair_Shop_Name.toString(),Toast.LENGTH_SHORT).show();
        textRepairShopName.setText(trim_Repair_Shop_Name.toString());
        //trim_Repair_Shop_Name = textRepairShopName.getText().toString();

        separate = getIntent.getExtras().getString("separateText","");
        textSeparate.setText(separate.toString());


        Matching matching = new Matching();
        matching.shopName = trim_Repair_Shop_Name.toString();
        matching.estimateNumber = Estimate_Num.toString();
        matching.userUID = UID.toString();

        matching.setEstimateNumber(Estimate_Num.toString());
        matching.setShopName(trim_Repair_Shop_Name.toString());
        matching.setUserUID(UID.toString());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        get_shop_UID = firebaseDatabase.getReference().child("shop_Infomation");
        get_shop_UID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("shopName").getValue(String.class).equals(trim_Repair_Shop_Name.toString())){
                        //동작 ok
                        key = dataSnapshot1.child("userID").getValue(String.class);
                        EstimateNum  estimateNum = new EstimateNum();
                        estimateNum.estimateNum = Estimate_Num.toString();
                        estimateNum.setEstimateNum(Estimate_Num.toString());

                        //Toast.makeText(getApplicationContext(),key.toString(),Toast.LENGTH_SHORT).show();

                        btnRepairShopInfo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SendEstimateActivityForCustomer.this,RepairShopInfomationDetailActivity.class);
                                intent.putExtra("shopID",key.toString());
                                startActivity(intent);
                            }
                        });

                        check_matching = firebaseDatabase.getReference("user_Estimate");
                        check_matching.child(estimateArray.get(6).toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child("nowState").getValue(String.class).equals("수리점 확인 완료")){
                                    //btnSelectShop.setClickable(false);
                                    //btnSelectShop.setText("수리점과 매칭 완료!");
                                    btnSelectShop.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            FirebaseDatabase.getInstance().getReference().child("shop_Infomation").child(key.toString()).child("vision_My_Repair_Estimate_Number").push().setValue(estimateNum);
                                            FirebaseDatabase.getInstance().getReference().child("matching_List").child(Estimate_Num.toString()).setValue(matching);
                                            FirebaseDatabase.getInstance().getReference().child("user_Estimate").child(Estimate_Num.toString()).child("nowState").setValue("수리 진행중")
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Intent intent = new Intent(SendEstimateActivityForCustomer.this,Matching_Complete_Activity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                        }
                                    });
                                }
                                else if(dataSnapshot.child("nowState").getValue(String.class).equals("수리 진행중")){
                                    btnSelectShop.setClickable(false);
                                    btnSelectShop.setText("수리점과 성사 되었습니다.수리 진행중 입니다.");
                                }
                                else if(dataSnapshot.child("nowState").getValue(String.class).equals("수리 완료!")){
                                    btnSelectShop.setClickable(true);
                                    btnSelectShop.setText("수리 완료! 터치하면 수리점 평가로 이동합니다");
                                    btnSelectShop.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(SendEstimateActivityForCustomer.this,Repute_Repair_Shop_Activity.class);
                                            intent.putExtra("repairShopName",trim_Repair_Shop_Name.toString()) ;
                                            intent.putExtra("estimateNumber",Estimate_Num.toString());
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else if(dataSnapshot.child("nowState").getValue(String.class).equals("수리 평가 완료")){
                                    btnSelectShop.setClickable(false);
                                    btnSelectShop.setText("수리점 평가가 완료되었습니다.");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*

        get_Repair_Shop_Estimate_Num_Reference = firebaseDatabase.getReference("users").child(UID);
        get_Repair_Shop_Estimate_Num_Reference.child("user_Estimate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    get_Repair_Shop_Estimate_Num = dataSnapshot1.child("estimateNum").getValue(String.class);
                }

                get_Repair_Shop_Estimate = firebaseDatabase.getReference("repair_Shop_Estimate");
                get_Repair_Shop_Estimate.child(get_Repair_Shop_Estimate_Num.toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            textForecastWrong.setText(dataSnapshot1.child("forecastWrong").getValue(String.class));
                            textPay.setText(dataSnapshot1.child("pay").getValue(String.class));
                            textRepairWay.setText(dataSnapshot1.child("repairWay").getValue(String.class));
                            textRepairDetail.setText(dataSnapshot1.child("repairDetail").getValue(String.class));
                            textRepairTakeTime.setText(dataSnapshot1.child("repairTime").getValue(String.class));
                            textShopName.setText(dataSnapshot1.child("repairShopName").getValue(String.class));
                        }
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
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(locationSource.onRequestPermissionsResult(requestCode,permissions,grantResults))
        {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        get_Repiar_Shop_Location_Ref = firebaseDatabase.getReference("shop_Infomation");
        get_Repiar_Shop_Location_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("shopName").getValue(String.class).equals(trim_Repair_Shop_Name.toString())){
                        //Toast.makeText(getApplicationContext(),"테스트",Toast.LENGTH_SHORT).show();
                        str_shop_latitude = dataSnapshot1.child("shopLatitude").getValue(String.class);
                        str_shop_longitude = dataSnapshot1.child("shopLongitude").getValue(String.class);

                        dou_shop_latitude = Double.parseDouble(str_shop_latitude);
                        dou_shop_longitude = Double.parseDouble(str_shop_longitude);
                        //Toast.makeText(getApplicationContext(),dou_shop_latitude+" "+dou_shop_longitude,Toast.LENGTH_SHORT).show();

                        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(dou_shop_latitude,dou_shop_longitude),12).animate(CameraAnimation.Linear);
                        naverMap.moveCamera(cameraUpdate);

                        Marker marker = new Marker();
                        InfoWindow infoWindow = new InfoWindow();
                        marker.setPosition(new LatLng(dou_shop_latitude,dou_shop_longitude));
                        marker.setWidth(Marker.SIZE_AUTO);
                        marker.setHeight(Marker.SIZE_AUTO);
                        String shopName = dataSnapshot1.child("shopName").getValue(String.class);
                        marker.setTag(shopName.toString());
                        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplicationContext()) {
                            @NonNull
                            @Override
                            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                                return shopName.toString();
                            }
                        });
                        infoWindow.setPosition(new LatLng(dou_shop_latitude,dou_shop_longitude));
                        String shopCity = dataSnapshot1.child("shopCity").getValue(String.class);
                        infoWindow.setOnClickListener(overlay -> {
                            String shopInfo = infoWindow.getMarker().getTag().toString()+"/"+shopCity.toString();
                            //intent.putExtra("shopName",infoWindow.getMarker().getTag().toString());
                            //startActivity(intent);
                            //infoWindow.getMarker().getTag().toString();
                            return true;
                        });
                        marker.setOnClickListener(overlay -> {
                            // 마커를 클릭할 때 정보창을 엶
                            if (marker.getInfoWindow() == null) {
                                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                                infoWindow.open(marker);
                            }
                            return true;
                        });
                        marker.setMap(naverMap);
                        //String shopCity = dataSnapshot1.child("shopCity").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //setGps();
        //locationProc();
        //naverMap.setLocationSource(locationSource);
        //naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        //CameraUpdate cameraUpdate = CameraUpdate.zoomTo(12);
        //naverMap.moveCamera(cameraUpdate);
    }

    private void setGps() {   //현재 위치를 자신의 위치로 변경하는 코드
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }
        locationProc();

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                locationListener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1,locationListener);
    }

    public void locationProc() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    //cameraPosition = new CameraPosition(new LatLng(location.getLatitude(),location.getLongitude()),12);
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    @Override
    protected void onStart() {
        estimate_NaverMap.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        estimate_NaverMap.onResume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        estimate_NaverMap.onStop();
        super.onStop();
    }

    @Override
    protected void onPause() {
        estimate_NaverMap.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        estimate_NaverMap.onDestroy();
        super.onDestroy();
    }



}
