package com.example.nsn11.dotcom;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.CameraUpdateParams;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

public class TmapSearchNearRepairShop extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();
    private MapView mapView;
    //private InfoWindow infoWindow;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int PERMISSION_CODE=1;
    private FusedLocationSource locationSource;
    Button btnSetMyLocation;
    public LocationManager locationManager=null;
    public LocationListener locationListener=null;
    CameraPosition cameraPosition;
    double longitude=0.0;
    double latitude=0.0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naver_map);

        btnSetMyLocation = (Button)findViewById(R.id.btnSetMyLocation);

        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("07x0q8dwaw"));
        mapView = (MapView)findViewById(R.id.map_view);
        mapView.getMapAsync(this);


        locationSource = new FusedLocationSource(this,LOCATION_PERMISSION_REQUEST_CODE);


    }

    public void addPoint()
    {
        m_mapPoint.add(new MapPoint("???????????????","??????",37.346119,126.746403,"1234"));
        m_mapPoint.add(new MapPoint("???????????? ?????????","??????",37.344549,126.748929,"1234"));
        m_mapPoint.add(new MapPoint("???????????? ?????? ????????????","??????",37.346582,126.732938,"1234"));
        m_mapPoint.add(new MapPoint("???????????????","??????",37.354545,126.722926,"1234"));
        m_mapPoint.add(new MapPoint("???????????????","??????",37.511015,126.768906,"1234"));
        m_mapPoint.add(new MapPoint("?????????","?????????",37.627329,126.434297,"1234"));
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
    public void onMapReady(@NonNull com.naver.maps.map.NaverMap naverMap) {
        setGps();
        locationProc();
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        CameraUpdate cameraUpdate = CameraUpdate.zoomTo(12);
        naverMap.moveCamera(cameraUpdate);


        btnSetMyLocation = (Button)findViewById(R.id.btnSetMyLocation);

        btnSetMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),latitude+" "+longitude,Toast.LENGTH_SHORT).show();
                CameraPosition cameraPosition = new CameraPosition(new LatLng(latitude,longitude),13);
                CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(latitude,longitude),12).animate(CameraAnimation.Linear);
                naverMap.moveCamera(cameraUpdate);
            }
        });

        addPoint();

        Intent intent = new Intent(TmapSearchNearRepairShop.this,MainActivity.class);

        for(int i=0;i<m_mapPoint.size();i++)
        {
            Marker marker = new Marker();
            InfoWindow infoWindow = new InfoWindow();
            marker.setPosition(new LatLng(m_mapPoint.get(i).getLatitude(),m_mapPoint.get(i).getLongitude()));
            marker.setWidth(Marker.SIZE_AUTO);
            marker.setHeight(Marker.SIZE_AUTO);
            String shopName = m_mapPoint.get(i).getName();
            marker.setTag(shopName.toString());
            infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getApplicationContext()) {
                @NonNull
                @Override
                public CharSequence getText(@NonNull InfoWindow infoWindow) {
                    return shopName.toString();
                }
            });
            infoWindow.setPosition(new LatLng(m_mapPoint.get(i).getLatitude(),m_mapPoint.get(i).getLongitude()));
            infoWindow.setOnClickListener(overlay -> {
                String shopInfo = infoWindow.getMarker().getTag().toString();
                /*
                Intent gotoEstimate = new Intent(TmapSearchNearRepairShop.this,EstimateRequest.class);
                gotoEstimate.putExtra("ShopLocation",shopInfo);
                setResult(RESULT_OK,gotoEstimate);
                finish();
                */
                Toast.makeText(getApplicationContext(),shopInfo,Toast.LENGTH_SHORT).show();
                //intent.putExtra("shopName",infoWindow.getMarker().getTag().toString());
                //startActivity(intent);
                //infoWindow.getMarker().getTag().toString();
                return true;
            });
            //infoWindow.open(marker);
            //infoWindow.open(naverMap);
            marker.setMap(naverMap);

            marker.setOnClickListener(overlay -> {
                // ????????? ????????? ??? ???????????? ???
                if (marker.getInfoWindow() == null) {
                    // ?????? ????????? ?????? ?????? ???????????? ?????? ?????? ???
                    infoWindow.open(marker);
                } else {
                    // ?????? ?????? ????????? ?????? ?????? ???????????? ?????? ??????
                    infoWindow.close();
                }
                return true;
            });


        }

    }

    private void setGps() {   //?????? ????????? ????????? ????????? ???????????? ??????
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }
        locationProc();

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // ????????? ???????????????(???????????? NETWORK_PROVIDER ??????)
                1, // ??????????????? ?????? ???????????? (miliSecond)
                1, // ??????????????? ?????? ???????????? (m)
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
}
