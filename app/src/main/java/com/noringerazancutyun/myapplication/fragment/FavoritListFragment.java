package com.noringerazancutyun.myapplication.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.adapter.FavoriteAdapter;
import com.noringerazancutyun.myapplication.models.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FavoritListFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<Statement> listStatement;


    public FavoritListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.favoritelist_activity, container, false);
        recyclerView = v.findViewById(R.id.favorit_recycler);
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getContext(), listStatement);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listStatement = new ArrayList<>();
//        listStatement.add(new Statement("90000", "Davitashen", "3", "8"));
//        listStatement.add(new Statement("75000", "Avan", "2", "3" ));
//        listStatement.add(new Statement("150000", "Kentron", "4", "2"));
//        listStatement.add(new Statement("90000", "Davitashen", "3", "3" ));
//        listStatement.add(new Statement("90000", "Davitashen", "3", "8"));
//        listStatement.add(new Statement("75000", "Avan", "2", "3" ));
//        listStatement.add(new Statement("150000", "Kentron", "4", "2"));
//        listStatement.add(new Statement("90000", "Davitashen", "3", "3" ));

    }
}
