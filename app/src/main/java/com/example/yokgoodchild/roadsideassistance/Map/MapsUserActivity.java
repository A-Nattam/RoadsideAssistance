package com.example.yokgoodchild.roadsideassistance.Map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsUserActivity extends AppCompatActivity {

    private MapView mMapView;
    private GoogleMap googleMap;
    private Activity activity;
    private ViewGroup root;
    private Marker marker;

    private ArrayList<RepairShopBean> listLocation;

    double lat = 0.0;
    double lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_user);

        int baseColor = ContextCompat.getColor(this, R.color.colorToolbar_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.maps_user_tiilbar);
        toolbar.setTitle("Request For Help");
        toolbar.setTitleTextColor(baseColor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapView = (MapView) findViewById(R.id.maps_user_mapview);
        mMapView.onCreate(savedInstanceState);

        String[] lat = {"18.795677", "18.788689", "18.797018"};
        String[] log = {"99.003954", "98.989106", "98.996187"};
        listLocation = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RepairShopBean r = new RepairShopBean();
            r.setLatitude(lat[i]);
            r.setLongitude(log[i]);

            listLocation.add(r);
        }

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(MapsUserActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsUserActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
//                setMaker(listLocation);
                myLocation();
//                googleMap.setMyLocationEnabled(true);
//                googleMap.setIndoorEnabled(true);
//                googleMap.setBuildingsEnabled(true);
//
//                Criteria criteria = new Criteria();
//                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                String provider = locationManager.getBestProvider(criteria, false);
//                Location location = locationManager.getLastKnownLocation(provider);
//                double lat = location.getLatitude();
//                double lng = location.getLongitude();
//                LatLng coordinate = new LatLng(lat, lng);
//
//                CameraUpdate center = CameraUpdateFactory.newLatLng(coordinate);
//                CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
//                googleMap.moveCamera(center);
//                googleMap.animateCamera(zoom);
            }
        });

    }

    private void myLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateLocation(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationLisener);
    }

    private void updateLocation(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();

            setMaker(listLocation,lat , lng);
        }
    }

    private void setMaker(ArrayList<RepairShopBean> listRepair, double lat, double lng) {
        LatLng setLatlnged = new LatLng(lat, lng);
        CameraUpdate cemarUpdate = CameraUpdateFactory.newLatLngZoom(setLatlnged, 15);
        for (RepairShopBean repair : listRepair) {
            LatLng setLatlngs = new LatLng(Double.parseDouble(repair.getLatitude()), Double.parseDouble(repair.getLongitude()));
//            CameraUpdate cemarUpdate = CameraUpdateFactory.newLatLngZoom(setLatlng, 200);
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(setLatlngs)
                    .title("Hello SetMarker")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
//            googleMap.animateCamera(cemarUpdate);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.animateCamera(cemarUpdate);
//        LatLng setLatlng = new LatLng(lat, lng);
//        CameraUpdate cemarUpdate = CameraUpdateFactory.newLatLngZoom(setLatlng, 15);
//        if (marker != null) marker.remove();
//        marker = googleMap.addMarker(new MarkerOptions()
//                .position(setLatlng)
//                .title("Hello Position")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        googleMap.setMyLocationEnabled(true);
//        googleMap.animateCamera(cemarUpdate);
    }

    android.location.LocationListener locationLisener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
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

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
