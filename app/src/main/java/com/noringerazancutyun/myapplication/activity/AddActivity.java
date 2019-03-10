package com.noringerazancutyun.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.models.Statement;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText descText, addressText, priceText;
    private static final int SELECT_PICTURE = 1;
    private String ImagePath;
    private ImageView ImageStatementBtn, saveStatementBtn;
    private String mCategory, mType, mFloor, mRooms, mDesc, mAdress, mLocation, mPrice;


    private Statement userStatement;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDataBaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        descText = findViewById(R.id.desc_edit);
        descText.setSelectAllOnFocus(true);
        addressText =findViewById(R.id.address_edit);
        priceText = findViewById(R.id.price_edit);

        ImageStatementBtn = findViewById(R.id.add_images);
        saveStatementBtn = findViewById(R.id.save_statement_btn);

        Spinner categorySpinner = findViewById(R.id.category_spiner);
        Spinner typeSpinner = findViewById(R.id.type_spiner);
        Spinner roomSpinner = findViewById(R.id.room_spinner);
        Spinner floorSpinner = findViewById(R.id.floor_spinner);
        Spinner locationSpinner = findViewById(R.id.location_spinner);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference("Statement");

        saveStatementBtn.setOnClickListener(this);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,R.array.category, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,R.array.type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> roomAdapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> locationAdaptewr = ArrayAdapter.createFromResource(this,R.array.location, android.R.layout.simple_spinner_item);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationAdaptewr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(categoryAdapter);
        typeSpinner.setAdapter(typeAdapter);
        roomSpinner.setAdapter(roomAdapter);
        floorSpinner.setAdapter(floorAdapter);
        locationSpinner.setAdapter(locationAdaptewr);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                mCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                mType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                mRooms = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                mFloor = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                mLocation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void writeStatementInfoToDB() {

        String desc = descText.getText().toString();
        String address = addressText.getText().toString();
        String price = priceText.getText().toString();
        userStatement = new Statement(mCategory, mType, price, mRooms, mFloor, mLocation, address, desc );
        mDataBaseReference.child(user.getUid()).setValue(userStatement);
    }

    @Override
    public void onClick(View view) {
        writeStatementInfoToDB();
        Intent intent = new Intent(AddActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
