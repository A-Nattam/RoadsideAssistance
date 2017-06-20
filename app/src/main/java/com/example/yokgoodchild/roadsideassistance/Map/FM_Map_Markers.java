package com.example.yokgoodchild.roadsideassistance.Map;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FM_Map_Markers extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private Activity activity;
    private ViewGroup root;
    private Marker marker;

    ArrayList<RepairShopBean> listLocation;

    double lat = 0.0;
    double lng = 0.0;

    public FM_Map_Markers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        root = (ViewGroup) inflater.inflate(R.layout.fragment_fm__map__markers, container, false);

        mMapView = (MapView) root.findViewById(R.id.fm_map_markers_mapview);
        mMapView.onCreate(savedInstanceState);
        Button btn_close = (Button) root.findViewById(R.id.fm_map_markers_mapview_btn_close);
        String[] lat = {"18.795677", "18.788689", "18.797018"};
        String[] log = {"99.003954", "98.989106", "98.996187"};
        listLocation = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RepairShopBean r = new RepairShopBean();
            r.setLatitude(lat[i]);
            r.setLongitude(log[i]);

            listLocation.add(r);
        }


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        final String lati = getArguments().getString("latitude");
        final String longi = getArguments().getString("longitude");

        Toast.makeText(activity, lati + " " + longi, Toast.LENGTH_SHORT).show();

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
//                myLocation();
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                googleMap.setMyLocationEnabled(true);
                setMakers(listLocation);

            }
        });

        return root;
    }

    private void setMakers(ArrayList<RepairShopBean> listRepair) {

        for(RepairShopBean repair : listRepair){
            LatLng setLatlng = new LatLng(Double.parseDouble(repair.getLatitude()), Double.parseDouble(repair.getLongitude()));
            CameraUpdate cemarUpdate = CameraUpdateFactory.newLatLngZoom(setLatlng, 200);
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(setLatlng)
                    .title("Hello SetMarker")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
            googleMap.animateCamera(cemarUpdate);
        }

//        LatLng setLatlng = new LatLng(lat, lng);
//        CameraUpdate cemarUpdate = CameraUpdateFactory.newLatLngZoom(setLatlng, 16);
//        if (marker != null) marker.remove();
//        marker = googleMap.addMarker(new MarkerOptions()
//                .position(setLatlng)
//                .title("Hello SetMarker")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
//        googleMap.animateCamera(cemarUpdate);
    }

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
