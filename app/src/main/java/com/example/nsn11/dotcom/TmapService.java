package com.example.nsn11.dotcom;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.naver.maps.map.NaverMapSdk;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class TmapService extends AppCompatActivity implements TMapView.OnCalloutRightButtonClickCallback{

    TMapView tMapView = null;
    TMapMarkerItem item=null;
    LinearLayout linTMap;
    Button btnMyLocation;

    public LocationManager locationManager=null;
    public LocationListener locationListener=null;
    private static final int PERMISSION_CODE=1;
    private final static int LOCATION_INTENT=2;

    private static int mMarkerID;
    private double longitude=0.0;
    private double latitude=0.0;

    private ArrayList<TMapPoint> m_tmapPoint = new ArrayList<TMapPoint>();
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();
    //Intent estimateIntent = new Intent(this,EstimateRequest.class);
    public String ShopLocation=null;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference user_estimateRef = null;
    DatabaseReference estimate_numRef=null;
    DatabaseReference shop_Location_Ref=null;
    String UID=null;
    String Estimate_UID=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.tmap_service);
        super.onCreate(savedInstanceState);

        //NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("07x0q8dwaw"));

        final Intent intent = new Intent(this,EstimateRequest.class);
        btnMyLocation = (Button)findViewById(R.id.btnMyLocation);
        linTMap = (LinearLayout)findViewById(R.id.linTMap);

        tMapView = new TMapView(TmapService.this);
        tMapView.setSKTMapApiKey("2c5dfbff-0828-4d9e-a268-ba850a7607fc");
        tMapView.setZoomLevel(13);
        linTMap.addView(tMapView);
        tMapView.setIconVisibility(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        shop_Location_Ref = firebaseDatabase.getReference("shop_Infomation");


        setGps();
        addPoint();
        showMarkerPoint();
        Toast.makeText(getApplicationContext(),"선택하려면 옆 체크박스를 클릭해주세요!",Toast.LENGTH_SHORT).show();

        tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                Toast.makeText(TmapService.this,tMapMarkerItem.getID()+"/"+tMapMarkerItem.getCalloutSubTitle()+" 클릭됨",Toast.LENGTH_SHORT).show();
                //**컴퓨터/부천 이런 형식  이름/위치
                ShopLocation = tMapMarkerItem.getID().toString()+"/"+tMapMarkerItem.getCalloutSubTitle().toString();
                Intent intent1 = new Intent(TmapService.this,EstimateRequest.class);
                intent1.putExtra("ShopLocation",ShopLocation);
                //startActivityForResult(intent1,RESULT_OK);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });



        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.setCenterPoint(longitude,latitude);
                tMapView.setLocationPoint(longitude,latitude);
            }
        });

    }


    /*
    public void addPoint()
    {
        shop_Location_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    m_mapPoint.add(new MapPoint(dataSnapshot1.child("shopName").getValue(String.class),dataSnapshot1.child("shopCity").getValue(String.class),
                            dataSnapshot1.child("shopLatitude").getValue(double.class),dataSnapshot1.child("shopLongitude").getValue(double.class),
                            dataSnapshot1.child("userID").getValue(String.class)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    */
    public void addPoint()
    {
        m_mapPoint.add(new MapPoint("클릭컴퓨터","시흥",37.346119,126.746403,"1234"));
        m_mapPoint.add(new MapPoint("주연테크 컴퓨터","시흥",37.344549,126.748929,"1234"));
        m_mapPoint.add(new MapPoint("주연테크 시화 이마트점","시흥",37.346582,126.732938,"1234"));
        m_mapPoint.add(new MapPoint("젊은사람들","시흥",37.354545,126.722926,"!234"));
        m_mapPoint.add(new MapPoint("테스트용도","부천",37.511015,126.768906,"1234"));
    }


    private void getShopLocation()
    {

    }

    public void showMarkerPoint()
    {       //마커설정
        addPoint();
        for(int i=0;i<m_mapPoint.size();i++)
        {
            TMapPoint point = new TMapPoint(m_mapPoint.get(i).getLatitude(),m_mapPoint.get(i).getLongitude());
            item = new TMapMarkerItem();
            Bitmap bitmap=null;
            bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.placeholder);

            item.setTMapPoint(point);
            item.setName(m_mapPoint.get(i).getName());
            item.setID(m_mapPoint.get(i).getName());
            item.setVisible(item.VISIBLE);
            item.setIcon(bitmap);

            //풍선뷰 안의 항목의 글을 지정
            item.setCalloutTitle(m_mapPoint.get(i).getName());
            item.setCalloutSubTitle(m_mapPoint.get(i).getCity());
            item.setCanShowCallout(true);
            item.setAutoCalloutVisible(false);


            Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(),R.mipmap.mapcheck);
            item.setCalloutRightButtonImage(bitmap1);


            //String strID = String.format("pmarker%d",mMarkerID++);
            tMapView.addMarkerItem(m_mapPoint.get(i).getName(),item);
            mArrayMarkerID.add(m_mapPoint.get(i).getName());
        }
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
                    tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
                    tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
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
    public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {

    }
}
