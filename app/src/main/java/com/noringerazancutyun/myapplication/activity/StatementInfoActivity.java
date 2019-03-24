package com.noringerazancutyun.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.adapter.StatementImageAdapter;
import com.noringerazancutyun.myapplication.models.Statement;
import com.noringerazancutyun.myapplication.models.UserInform;
import com.noringerazancutyun.myapplication.roomDB.DatabaseHelper;
import com.noringerazancutyun.myapplication.roomDB.StatData;
import com.noringerazancutyun.myapplication.util.App;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatementInfoActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private TextView mStatName, mStatEmail, mStatphone, mStatPrice, mStatRooms,
    mStatFloor, mStatAddress, mStatDesc, mStatCategory, mStatType;
    private ImageView mStatFavorite, callBtn;
    private CircleImageView mStatUserImage;

    private GoogleMap mMap;


    private FirebaseAuth mAuth;
    private FirebaseUser user;


    RecyclerView mRecycler;
    StatementImageAdapter imageAdapter;
    private DatabaseReference mDataref;
    private List<String> mImages;

    private String myID;
    private String userID;
    private double lat, lng;
    private Toolbar toolbar;

    private String telNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement_info);
        mStatAddress = findViewById(R.id.stat_address);
        mStatName = findViewById(R.id.stat_user_name);
        mStatEmail = findViewById(R.id.stat_user_email);
        mStatphone = findViewById(R.id.stat_user_phone);
        mStatPrice = findViewById(R.id.stat_price);
        mStatRooms = findViewById(R.id.stat_rooms);
        mStatFloor = findViewById(R.id.stat_floor);
        mStatDesc = findViewById(R.id.stat_desc);
        mStatCategory = findViewById(R.id.stat_category);
        mStatType = findViewById(R.id.stat_type);
        mStatFavorite = findViewById(R.id.stat_favorite);
        mStatUserImage = findViewById(R.id.stat_user_image);
        callBtn = findViewById(R.id.call_btn);
        toolbar = findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mRecycler = findViewById(R.id.stat_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false ));
        mImages = new ArrayList<>();
        mDataref = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        myID = intent.getStringExtra("statId");
        userID = intent.getStringExtra("userID");
        lat = intent.getDoubleExtra("lat", 0.0d);
        lng = intent.getDoubleExtra("lng", 0.0d);
        recyclerConstructor();

        readStatData();
        readUserData();

        mStatFavorite.setOnClickListener(this);
        callBtn.setOnClickListener(this);

    }



    public void recyclerConstructor(){

        mDataref.child("Statement").child(myID).child("imageList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    String images = postSnap.getValue(String.class);
                    mImages.add(images);
                }
                imageAdapter = new StatementImageAdapter(StatementInfoActivity.this, mImages, myID);
                mRecycler.setAdapter(imageAdapter);

                toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
                toolbar.setTitle("" + mImages.size() + " Photo");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readStatData() {

        mDataref.child("Statement").child(myID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Statement stat = dataSnapshot.getValue(Statement.class);

                mStatPrice.setText(stat.getPrice());
                mStatAddress.setText(stat.getAddress());
                mStatCategory.setText(stat.getCategory());
                mStatType.setText(stat.getType());
                mStatDesc.setText(stat.getDesc());
                mStatFloor.setText(stat.getFloor());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readUserData(){
        mDataref.child("User").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserInform userinfo = dataSnapshot.getValue(UserInform.class);

                mStatName.setText(userinfo.getmUserName() + " " + userinfo.getmUserSurname());
                mStatEmail.setText(userinfo.getmUserEmail());
                mStatphone.setText(userinfo.getmUserPhoneNumber());
                Glide.with(StatementInfoActivity.this)
                        .load(userinfo.getmImageUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.avatar_icon)
                        .fitCenter()
                        .into(mStatUserImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.isMyLocationEnabled();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Yerevan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13f));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.stat_favorite):
                createFavorite();
                break;
        }
        switch (v.getId()) {
            case (R.id.call_btn):
                callUser();
                break;
        }

    }

    private void callUser() {

            Intent intentCall=new Intent(Intent.ACTION_CALL);
            telNum=mStatphone.getText().toString();
            intentCall.setData(Uri.parse("tel:" + "+"+telNum));

            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"Please grant permission",Toast.LENGTH_SHORT).show();
                requestPermission();
            }else {
                startActivity(intentCall);
            }


    }

    private  void requestPermission(){
        ActivityCompat.requestPermissions(StatementInfoActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }


    private void createFavorite() {

        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        StatData model = new StatData();
        model.setStatID(myID);
        model.setUserID(userID);
        model.setPrice(mStatPrice.getText().toString());
        model.setAddress(mStatAddress.getText().toString());
        model.setRoom(mStatRooms.getText().toString());
        model.setFloor(mStatFloor.getText().toString());
        model.setImageUrl(mImages.get(0));
        databaseHelper.getDataDao().insert(model);

        finish();
    }
}
