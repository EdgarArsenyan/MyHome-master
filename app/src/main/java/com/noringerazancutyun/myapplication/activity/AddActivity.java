package com.noringerazancutyun.myapplication.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.models.Statement;
import com.noringerazancutyun.myapplication.roomDB.DatabaseHelper;
import com.noringerazancutyun.myapplication.roomDB.MyStatData;
import com.noringerazancutyun.myapplication.roomDB.StatData;
import com.noringerazancutyun.myapplication.util.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_REQUEST_CODE = 1;


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDataBaseReference;
    private StorageReference mReference;
    private Uri imageUri;
    public ProgressDialog mProgressDialog;

    private EditText descText, addressText, priceText;
    private String  statID;
    private ImageView imageStatementBtn, saveStatementBtn, uploadImageBtn, homeImageView;
    private String mCategory, mType, mFloor, mRooms, mDesc, mAdress, mLocation, mPrice, userId;
    private double lat, lng;

    private Statement userStatement;
    private ArrayList<String> imageList = new ArrayList<>();
    private ArrayList<String> statList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        descText = findViewById(R.id.desc_edit);
        descText.setSelectAllOnFocus(true);
        addressText = findViewById(R.id.address_edit);
        priceText = findViewById(R.id.price_edit);

        imageStatementBtn = findViewById(R.id.add_images);
        saveStatementBtn = findViewById(R.id.save_statement_btn);
        uploadImageBtn = findViewById(R.id.upload_images);
        homeImageView = findViewById(R.id.home_img);
        Intent intent = getIntent();
        statID = intent.getStringExtra("statID");


        Spinner categorySpinner = findViewById(R.id.category_spiner);
        Spinner typeSpinner = findViewById(R.id.type_spiner);
        Spinner roomSpinner = findViewById(R.id.room_spinner);
        Spinner floorSpinner = findViewById(R.id.floor_spinner);
        Spinner locationSpinner = findViewById(R.id.location_spinner);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mReference = FirebaseStorage.getInstance().getReference("images").child(user.getUid());
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        mProgressDialog = new ProgressDialog(AddActivity.this);

        saveStatementBtn.setOnClickListener(this);
        uploadImageBtn.setOnClickListener(this);
        imageStatementBtn.setOnClickListener(this);
        addressText.setOnClickListener(this);

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

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                mCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                mType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                mRooms = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                mFloor = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) parent.getChildAt(0)).setTextSize(18);
                mLocation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getLatLngFromAddress() {
        List<Address> mAddress = new ArrayList<>();
        Geocoder geoCoder = new Geocoder(AddActivity.this);



        try {

            mAddress = geoCoder.getFromLocationName(addressText.getText().toString(), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mAddress.size() > 0) {
            Address location = mAddress.get(0);
            lat = location.getLatitude();
            lng = location.getLongitude();
        }
    }

    private void writeStatementInfoToDB() {


        if(statID != null){
            getLatLngFromAddress();
            mDesc = descText.getText().toString();
            mAdress = addressText.getText().toString();
            mPrice = priceText.getText().toString();
            userId = user.getUid();
            userStatement = new Statement(mCategory, mType, mPrice, mRooms, mFloor, mLocation, mAdress, mDesc, lat, lng, imageList, userId, statID );
            mDataBaseReference.child("Statement").child(statID).setValue(userStatement);

            createMyStat(statID, mAdress, mPrice, mRooms, mFloor);
        }else {

            final String uploadId = mDataBaseReference.push().getKey();

            getLatLngFromAddress();
            mDesc = descText.getText().toString();
            mAdress = addressText.getText().toString();
            mPrice = priceText.getText().toString();
            userId = user.getUid();
            userStatement = new Statement(mCategory, mType, mPrice, mRooms, mFloor, mLocation, mAdress, mDesc, lat, lng, imageList, userId, uploadId);
            mDataBaseReference.child("Statement").child(uploadId).setValue(userStatement);

            createMyStat(uploadId, mAdress, mPrice, mRooms, mFloor);
        }

    }

    private void createMyStat(String ID, String address, String price, String rooms, String floors) {

        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();

        MyStatData model = new MyStatData();
        model.setStatID(ID);
        model.setPrice(price);
        model.setAddress(address);
        model.setRoom(rooms);
        model.setFloor(floors);
        if(imageList != null) {
            model.setImageUrl(imageList.get(0));
        }else model.setImageUrl("no Photo");
        databaseHelper.getMyDataDao().insert(model);
        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.save_statement_btn):
                writeStatementInfoToDB();
                Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case (R.id.add_images):
                openGallery();
                break;
            case (R.id.upload_images):
                uploadFlie();
                break;
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFlie() {
         if(imageUri !=null){
             if (getFileExtension(imageUri).equals("jpg")) {
                 final StorageReference fileReference = mReference.child(System.currentTimeMillis() + ".jpg");


                 fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 imageList.add(uri.toString());

                             }
                         });

                     }
                 });
                 Toast.makeText(AddActivity.this, "UPLOAD", Toast.LENGTH_SHORT).show();

             }else{
                 Toast.makeText(this, "Unrecognized file extension", Toast.LENGTH_SHORT).show();
             }


         }else{
             Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
         }
    }

    public void openGallery() {
        Intent galleryAction = new Intent();
        galleryAction.setType("image/*");
        galleryAction.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryAction, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK &&
        data != null && data.getData() != null){
            imageUri = data.getData();
            Glide.with(this)
                    .load(imageUri)
                    .into(homeImageView);
        }
    }
}
