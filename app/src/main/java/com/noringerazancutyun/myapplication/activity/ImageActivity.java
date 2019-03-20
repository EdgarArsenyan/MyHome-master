package com.noringerazancutyun.myapplication.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.adapter.StatementImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    RecyclerView mRecycler;
    StatementImageAdapter imageAdapter;
    private DatabaseReference mDataref;
    private List<String> mImages;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        mRecycler = findViewById(R.id.image_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false ));
        mImages = new ArrayList<>();
        mDataref = FirebaseDatabase.getInstance().getReference().child("Statement");

        recyclerConstructor();
    }

    public void recyclerConstructor(){

        mDataref.child(id).child("imageList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnap: dataSnapshot.getChildren()) {
                    String images = postSnap.getValue(String.class);
                    mImages.add(images);
                }
                imageAdapter = new StatementImageAdapter(ImageActivity.this, mImages, id);
                mRecycler.setAdapter(imageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
