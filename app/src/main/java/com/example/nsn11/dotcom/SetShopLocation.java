package com.example.nsn11.dotcom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;


public class SetShopLocation extends AppCompatActivity implements TMapView.OnClickListenerCallback {
    Button btnSetMyLocation, btnPing;
    LinearLayout linTmap,linMap;
    double longitude = 0.0;
    double latitude = 0.0;
    String shop_longitude = "";
    String shop_latitude = "";
    TMapView tMapView = null;
    TMapMarkerItem item = null;
    TMapPoint tMapPoint = null;
    public LocationManager locationManager = null;
    public LocationListener locationListener = null;
    private static final int PERMISSION_CODE = 1;
    Display display = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_shop_location);

        btnSetMyLocation = (Button) findViewById(R.id.btnSetMyLocation);
        btnPing = (Button) findViewById(R.id.btnPing);
        linTmap = (LinearLayout) findViewById(R.id.linTmap);
        linMap = (LinearLayout)findViewById(R.id.linMap);

        tMapView = new TMapView(SetShopLocation.this);
        tMapView.setSKTMapApiKey("2c5dfbff-0828-4d9e-a268-ba850a7607fc");
        linTmap.addView(tMapView);
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.point);
        tMapView.setIcon(bitmap);
        tMapView.setIconVisibility(true);
        tMapView.setZoomLevel(18);
        tMapView.setMapPosition(TMapView.POSITION_DEFAULT);
        tMapPoint = tMapView.getCenterPoint();


        setGps();
        btnSetMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.setCenterPoint(longitude, latitude);
                tMapView.setLocationPoint(longitude, latitude);
            }
        });


        /*
        tMapView.setOnClickListener(new TMapView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),String.valueOf(v.getId()),Toast.LENGTH_SHORT).show();
                float x = v.getX();
                float y = v.getY();

                Bitmap bitmap=null;
                bitmap= BitmapFactory.decodeResource(getApplication().getResources(),R.mipmap.placeholder);

                //item = new TMapMarkerItem();
                //item.setIcon(bitmap);
                //item.setPosition(x,y);
                //tMapView.bringMarkerToFront(item);

                tMapPoint = tMapView.convertPointToGps(x,y);
                shop_latitude = String.valueOf(tMapPoint.getLatitude());
                shop_longitude = String.valueOf(tMapPoint.getLongitude());
            }
        });
        */

        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void setGps() {   //현재 위치를 자신의 위치로 변경하는 코드
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
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
    public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
        //터치한 곳의 위치좌표를 알려주는 코드
        //Toast.makeText(getApplicationContext(),String.valueOf(tMapPoint.getLatitude()) +" "+ String.valueOf(tMapPoint.getLongitude()) ,Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {

        Bitmap bitmap=null;
        bitmap= BitmapFactory.decodeResource(getApplication().getResources(),R.mipmap.placeholder);

        //터치한 위치에 좌표를 찍는 코드
        TMapPoint tMapPoint2 = new TMapPoint(tMapPoint.getLatitude(),tMapPoint.getLongitude());
        //item.setTMapPoint(tMapPoint2);
        item = new TMapMarkerItem();
        item.setTMapPoint(tMapPoint2);
        item.setIcon(bitmap);
        tMapView.addMarkerItem("수리점 위치",item);
        tMapView.bringMarkerToFront(item);
        shop_latitude = String.valueOf(tMapPoint.getLatitude());
        shop_longitude = String.valueOf(tMapPoint.getLongitude());
        return true;
    }

    /*
    public void SetPing()
    {
        Bitmap bitmap=null;
        bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.placeholder);

    }
    */


}