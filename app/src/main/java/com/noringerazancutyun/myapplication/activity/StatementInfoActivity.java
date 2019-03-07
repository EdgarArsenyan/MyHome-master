package com.noringerazancutyun.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.noringerazancutyun.myapplication.R;

public class StatementInfoActivity extends AppCompatActivity {

    TextView mStatName, mStatEmail, mStatphone, mStatPrice, mStatRooms,
    mStatFloor, mStatAddress, mStatDesc, mStatCategory, mStatType;
    ImageView mStatFavorite, mStatImage, mStatUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement_info);
        mStatAddress =findViewById(R.id.stat_address);
        mStatName =findViewById(R.id.stat_user_name);
        mStatEmail =findViewById(R.id.stat_user_email);
        mStatphone =findViewById(R.id.stat_user_phone);
        mStatPrice =findViewById(R.id.stat_price);
        mStatRooms =findViewById(R.id.stat_rooms);
        mStatFloor =findViewById(R.id.stat_floor);
        mStatDesc =findViewById(R.id.stat_desc);
        mStatCategory =findViewById(R.id.stat_category);
        mStatType =findViewById(R.id.stat_type);
        mStatFavorite =findViewById(R.id.stat_favorite);
        mStatImage =findViewById(R.id.stat_image);
        mStatUserImage =findViewById(R.id.stat_user_image);
    }

}
