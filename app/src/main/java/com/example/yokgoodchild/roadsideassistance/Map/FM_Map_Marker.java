package com.example.yokgoodchild.roadsideassistance.Map;


import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FM_Map_Marker extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private Activity activity;
    private ViewGroup root;
    private Marker marker;

    double lat = 0.0;
    double lng = 0.0;

    public FM_Map_Marker() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        root = (ViewGroup) inflater.inflate(R.layout.fragment_fm__map__marker, container, false);
        // Inflate the layout for this fragment

        mMapView = (MapView) root.findViewById(R.id.fm_map_marker_mapview);
        mMapView.onCreate(savedInstanceState);
        Button btn_close = (Button) root.findViewById(R.id.fm_map_marker_mapview_btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        final String lati = getArguments().getString("latitude");
        final String longi = getArguments().getString("longitude");

        Toast.makeText(activity, lati+" "+longi, Toast.LENGTH_SHORT).show();

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
                setMaker2(Double.parseDouble(lati),Double.parseDouble(longi));

            }
        });

        return root;
    }

    LocationListener locationLisener = new LocationListener() {
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

    private void updateLocation(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            setMaker(lat, lng);
        }
    }

    private void setMaker(double lat, double lng) {
        LatLng setLatlng = new LatLng(lat, lng);
        CameraUpdate cemarUpdate = CameraUpdateFactory.newLatLngZoom(setLatlng, 16);
        if (marker != null) marker.remove();
        marker = googleMap.addMarker(new MarkerOptions()
                .position(setLatlng)
                .title("Hello Position")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
        googleMap.animateCamera(cemarUpdate);
    }

    private void setMaker2(double lat, double lng) {
        LatLng setLatlng = new LatLng(lat, lng);
        CameraUpdate cemarUpdate = CameraUpdateFactory.newLatLngZoom(setLatlng, 16);
        if (marker != null) marker.remove();
        marker = googleMap.addMarker(new MarkerOptions()
                .position(setLatlng)
                .title("Hello SetMarker")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
        googleMap.animateCamera(cemarUpdate);
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
