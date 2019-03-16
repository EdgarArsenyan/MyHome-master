package com.noringerazancutyun.myapplication.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.adapter.ImageAdapter;
import com.noringerazancutyun.myapplication.adapter.StatementImageAdapter;
import com.noringerazancutyun.myapplication.models.Images;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    RecyclerView mRecycler;
    StatementImageAdapter imageAdapter;
    private DatabaseReference mDataref;
    private List<String> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        mRecycler = findViewById(R.id.image_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false ));
        mImages = new ArrayList<>();
        mDataref = FirebaseDatabase.getInstance().getReference().child("Statement");

        recyclerConstructor();
    }

    public void recyclerConstructor(){

        mDataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnap: dataSnapshot.child(user.getUid()).child("mImages").getChildren()) {
                    String images = postSnap.getValue(String.class);
                    mImages.add(images);
                }
                imageAdapter = new StatementImageAdapter(ImageActivity.this, mImages);
                mRecycler.setAdapter(imageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
