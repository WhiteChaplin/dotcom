package com.example.nsn11.dotcom;

import android.content.Context;
import android.graphics.Canvas;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

public class MapView extends View {
    Button btnSetMyLocation,btnPing;
    LinearLayout linTmap;
    double longitude=0.0;
    double latitude=0.0;
    String shop_longitude="";
    String shop_latitude="";
    double x;
    double y;
    TMapView tMapView=null;
    TMapMarkerItem item = null;
    TMapPoint tMapPoint=null;
    public LocationManager locationManager=null;
    public LocationListener locationListener = null;
    private static final int PERMISSION_CODE=1;
    Display display = null;
    public MapView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
