package com.noringerazancutyun.myapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.activity.EmailPasswordActivity;
import com.noringerazancutyun.myapplication.activity.StatementInfoActivity;
import com.noringerazancutyun.myapplication.models.Statement;
import com.noringerazancutyun.myapplication.util.GeocodingLocation;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {



    private double lat, lng;
    Statement mStatement = new Statement();
    String address = mStatement.getAddress();
    List<Address> mAddress;
    String locality;

    SupportMapFragment mapFragment;
    private GoogleMap mMap;


    public MapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_map2, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment==null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return view ;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//       LatLng yerevan = new LatLng(40.190018, 44.509153);
        LatLng loc = getLatLngFromAddress();
        mMap.addMarker(new MarkerOptions().position(loc).title("Yerevan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 11f));
        mMap.isMyLocationEnabled();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Toast.makeText(getContext(), locality, Toast.LENGTH_LONG).show();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(getContext(), StatementInfoActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private LatLng getLatLngFromAddress(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    Geocoder geoCoder = new Geocoder(getContext());
                    List<Address> mAddress = geoCoder.getFromLocationName("London", 1);
                    Address location = mAddress.get(0);
                    locality = location.getLocality();
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        };
        thread.start();
        return new LatLng(lat, lng);
    }
}
