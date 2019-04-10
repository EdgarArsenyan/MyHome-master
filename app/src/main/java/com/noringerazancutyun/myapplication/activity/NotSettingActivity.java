package com.noringerazancutyun.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.fragment.NotAddFragment;


/**
 * this activity is not yet written
 */

public class NotSettingActivity extends AppCompatActivity implements NotAddFragment.OnInputSelected{

    ImageView addNotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_setting);

        addNotButton = findViewById(R.id.not_add);

        addNotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotAddFragment add = new NotAddFragment();
//                add.show(getFragmentManager(),  "NotAddFragment");

            }
        });
    }

    @Override
    public void sendInput(String search, String loc) {

    }
}
