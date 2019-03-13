package com.noringerazancutyun.myapplication.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_CODE = 10;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDataBaseReference;
    private StorageReference mReference;
    private Uri imageUri;
    public ProgressDialog mProgressDialog;

    private EditText descText, addressText, priceText;
    private static final int SELECT_PICTURE = 1;
    private String imagePath;
    private ImageView imageStatementBtn, saveStatementBtn, uploadImageBtn, homeImageView;
    private String mCategory, mType, mFloor, mRooms, mDesc, mAdress, mLocation, mPrice;


    private Statement userStatement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        descText = findViewById(R.id.desc_edit);
        descText.setSelectAllOnFocus(true);
        addressText =findViewById(R.id.address_edit);
        priceText = findViewById(R.id.price_edit);

        imageStatementBtn = findViewById(R.id.add_images);
        saveStatementBtn = findViewById(R.id.save_statement_btn);
        uploadImageBtn = findViewById(R.id.upload_images);
        homeImageView = findViewById(R.id.home_img);

        Spinner categorySpinner = findViewById(R.id.category_spiner);
        Spinner typeSpinner = findViewById(R.id.type_spiner);
        Spinner roomSpinner = findViewById(R.id.room_spinner);
        Spinner floorSpinner = findViewById(R.id.floor_spinner);
        Spinner locationSpinner = findViewById(R.id.location_spinner);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mReference = FirebaseStorage.getInstance().getReference("images").child(user.getUid());
        mDataBaseReference = FirebaseDatabase.getInstance().getReference("images");
        mProgressDialog = new ProgressDialog(AddActivity.this);

        saveStatementBtn.setOnClickListener(this);
        uploadImageBtn.setOnClickListener(this);
        imageStatementBtn.setOnClickListener(this);

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

    private void writeStatementInfoToDB() {

        mDesc = descText.getText().toString();
        mAdress = addressText.getText().toString();
        mPrice = priceText.getText().toString();
        userStatement = new Statement(mCategory, mType, mPrice, mRooms, mFloor, mLocation, mAdress, mDesc);
        mDataBaseReference.child(user.getUid()).setValue(userStatement);
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
        }

    }

    private void uploadFlie() {
         if(imageUri !=null){
             final StorageReference fileReference = mReference.child(System.currentTimeMillis() + ".jpg");

             fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     final String uploadId = mDataBaseReference.push().getKey();
                     fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             mDataBaseReference.child(uploadId).child("mHomeImage").setValue(uri.toString());

                         }
                     });

                 }
             });
             Toast.makeText(AddActivity.this, "UPLOAD", Toast.LENGTH_SHORT).show();

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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                cameraPermissionResult(true);
//            } else {
//                cameraPermissionResult(false);
//            }
//        }
//
//
//    }
//
//    private void getCameraPermission() {
//        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(AddActivity.this,
//                    new String[]{Manifest.permission.CAMERA},
//                    CAMERA_PERMISSION_CODE);
//
//        } else {
//            cameraPermissionResult(true);
//        }
//    }
//
//    private void cameraPermissionResult(boolean cameraPermissionGranted) {
//        if (cameraPermissionGranted) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        } else {
//            Toast.makeText(AddActivity.this, "CANCEL",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
//
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
////            mUserImage.setImageBitmap(photo);
//
//
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.show();
//        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
//
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.show();
//
//            imageUri = data.getData();
//            final StorageReference myPath = mReference.child("FolderS").child(imageUri.getLastPathSegment() + ".jpg");
//            myPath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    Statement mStatement = new Statement(myPath.getDownloadUrl().toString());
//                    String imageId = mDataBaseReference.push().getKey();
//                    mDataBaseReference.child(imageId).setValue(mStatement);
//
////                    myPath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                        @Override
////                        public void onSuccess(Uri uri) {
////                            mDataBaseReference.child(user.getUid()).child("mImageUrl").setValue(uri.toString());
////
////                        }
////                    });
//                }
//            });
//
//        }
//
//    }


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
