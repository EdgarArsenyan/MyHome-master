package com.noringerazancutyun.myapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.activity.EmailPasswordActivity;
import com.noringerazancutyun.myapplication.activity.StatementInfoActivity;
import com.noringerazancutyun.myapplication.models.Statement;
import com.noringerazancutyun.myapplication.models.UserInform;
import com.noringerazancutyun.myapplication.util.GeocodingLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDataBaseReference;

    private double lat, lng;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    Statement stat;
    Map<Marker, Statement > map = new HashMap<>();






    public MapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map2, container, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference("Statement");

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);


        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        readStatementInfoFromDB();

        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.isMyLocationEnabled();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Statement s = map.get(marker);
                Intent intent = new Intent(getContext(), StatementInfoActivity.class);
                intent.putExtra("statId", s.getStatID());
                intent.putExtra("lat", s.getLat());
                intent.putExtra("lng", s.getLng());
                intent.putExtra("userID", s.getUserId());
                startActivity(intent);
                return false;
            }
        });


    }

    private void readStatementInfoFromDB() {
        mDataBaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot myData: dataSnapshot.getChildren()) {

                    Statement stat = myData.getValue(Statement.class);

                    lat = myData.child("lat").getValue(Double.class);
                     lng = myData.child("lng").getValue(Double.class);
                     String price = myData.child("price").getValue(String.class);
                    Log.d(TAG, "onDataChange: " + "" + lat + lng);

                   Marker marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(price));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13f));
                    map.put(marker, stat);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
