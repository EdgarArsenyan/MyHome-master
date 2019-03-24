package com.noringerazancutyun.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.adapter.FavoriteAdapter;
import com.noringerazancutyun.myapplication.adapter.MyStatAdapter;
import com.noringerazancutyun.myapplication.roomDB.DatabaseHelper;
import com.noringerazancutyun.myapplication.roomDB.MyStatData;
import com.noringerazancutyun.myapplication.roomDB.StatData;
import com.noringerazancutyun.myapplication.util.App;

public class MyStat extends AppCompatActivity implements MyStatAdapter.OnDeleteListener {

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private DatabaseReference mDataref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stat);
        recyclerView = findViewById(R.id.my_stat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();

    }

    @Override
    public void onResume() {
        super.onResume();
        MyStatAdapter Adapter = new MyStatAdapter(this, databaseHelper.getMyDataDao().getAllData());
        Adapter.setOnDeleteListener(this);
        recyclerView.setAdapter(Adapter);
    }

    @Override
    public void onDelete(MyStatData dataModel) {
        databaseHelper.getMyDataDao().delete(dataModel);
    }
}
