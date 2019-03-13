package com.noringerazancutyun.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.adapter.StatementImageAdapter;
import com.noringerazancutyun.myapplication.models.Images;

import java.util.ArrayList;
import java.util.List;

public class StatementInfoActivity extends AppCompatActivity{

    TextView mStatName, mStatEmail, mStatphone, mStatPrice, mStatRooms,
    mStatFloor, mStatAddress, mStatDesc, mStatCategory, mStatType;
    ImageView mStatFavorite, mStatUserImage;

    Images img = new Images();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    RecyclerView mRecycler;
    StatementImageAdapter imageAdapter;
    private DatabaseReference mDataref;
    private List<Images> mImages;
    Toolbar toolbar;
      int s = 1;


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
        toolbar = findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        mRecycler = findViewById(R.id.stat_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false ));
        mImages = new ArrayList<>();
        mDataref = FirebaseDatabase.getInstance().getReference("images");

        recyclerConstructor();

    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setTitle(String.valueOf(s) + " Photo");
    }

    public void recyclerConstructor(){

        mDataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    Images images = postSnap.getValue(Images.class);
                    mImages.add(images);
                    s++;
                }
                imageAdapter = new StatementImageAdapter(StatementInfoActivity.this, mImages);
                mRecycler.setAdapter(imageAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
