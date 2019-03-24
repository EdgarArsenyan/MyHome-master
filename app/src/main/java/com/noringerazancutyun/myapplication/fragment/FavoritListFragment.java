package com.noringerazancutyun.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.adapter.FavoriteAdapter;
import com.noringerazancutyun.myapplication.roomDB.DatabaseHelper;
import com.noringerazancutyun.myapplication.roomDB.StatData;
import com.noringerazancutyun.myapplication.util.App;

public class FavoritListFragment extends Fragment implements FavoriteAdapter.OnDeleteListener{

    View v;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;


    public FavoritListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.favoritelist_activity, container, false);
        recyclerView = v.findViewById(R.id.favorit_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        databaseHelper = App.getInstance().getDatabaseInstance();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        FavoriteAdapter Adapter = new FavoriteAdapter(getContext(), databaseHelper.getDataDao().getAllData());
        Adapter.setOnDeleteListener(this);
        recyclerView.setAdapter(Adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onDelete(StatData dataModel) {
        databaseHelper.getDataDao().delete(dataModel);
    }
}
