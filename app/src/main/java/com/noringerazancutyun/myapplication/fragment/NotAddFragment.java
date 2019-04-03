package com.noringerazancutyun.myapplication.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.activity.NotSettingActivity;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotAddFragment extends DialogFragment {
    String searchCategory, searchLocation;

    ImageView okButton, cancelButton;




    public interface OnInputSelected{
        void sendInput(String search, String loc);
    }

    public SearchFragment.OnInputSelected onInputSelected;


    public NotAddFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        okButton = view.findViewById(R.id.ok_btn);
        cancelButton = view.findViewById(R.id.cancel_btn);

        Spinner locSpinner = view.findViewById(R.id.loc_search);
        Spinner catSpinner = view.findViewById(R.id.cat_search);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(getContext(), R.array.location, android.R.layout.simple_spinner_item);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        catSpinner.setAdapter(categoryAdapter);
        locSpinner.setAdapter(locationAdapter);


        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                searchCategory = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: " + searchCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                searchLocation = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: " + searchLocation);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchCategory;
                String loc = searchLocation;
                onInputSelected.sendInput(search, loc);
                getDialog().dismiss();
            }
        });

        return view;
    }

    public void setOnInputSelected(SearchFragment.OnInputSelected onInputSelected) {
        this.onInputSelected = onInputSelected;
    }
}
