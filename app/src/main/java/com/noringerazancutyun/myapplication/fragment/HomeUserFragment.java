package com.noringerazancutyun.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.activity.EmailPasswordActivity;


public class HomeUserFragment extends Fragment {

    ImageView  mOkButton;

    public HomeUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_user, container, false);


        mOkButton = v.findViewById(R.id.create_img);


        clickCreate();
        return v;
    }



    public void clickCreate(){
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EmailPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
