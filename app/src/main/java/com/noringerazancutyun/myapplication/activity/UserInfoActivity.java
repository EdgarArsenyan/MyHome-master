package com.noringerazancutyun.myapplication.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.models.UserInform;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_CODE = 10;

    private EditText mName, mSurname, mPhone;
    private ImageView mSaveButton, mUserImage;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDataBaseReference;
    private StorageReference mReference;
    private String userID;
    private String urlImage;
     UserInform userInfo;
     private  Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mName = findViewById(R.id.user_name);
        mSurname = findViewById(R.id.user_surname);
        mPhone = findViewById(R.id.user_phone);
        mUserImage = findViewById(R.id.user_image);
        mSaveButton = findViewById(R.id.create_img_register_activity);

        mProgressDialog = new ProgressDialog(UserInfoActivity.this);

        registerForContextMenu(mUserImage);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference("User");
        mReference = FirebaseStorage.getInstance().getReference();
        userID = user.getUid();

        mSaveButton.setOnClickListener(this);
//        writeFromDatabase();


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.gallery_or_camera_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.from_gallery:
                openGallery();
                break;
            case R.id.from_camera:
                getCameraPermission();
                break;
            case R.id.cancel:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void writeToDataBase() {

        String name = mName.getText().toString().trim();
        String surname = mSurname.getText().toString();
        String phone = mPhone.getText().toString();
        String email = user.getEmail();
        String image;
        if (urlImage!= null)
             image = urlImage;
        else image = "no photo";
        userInfo = new UserInform(name, email, surname, phone, image);
        mDataBaseReference.child(user.getUid()).setValue(userInfo);


    }
//    private void writeFromDatabase(){
//        if(userInfo.getmUserName()!=null && userInfo.getmUserSurname()!= null && userInfo.getmUserPhoneNumber() !=null) {
//            mName.setText(userInfo.getmUserName());
//            mSurname.setText(userInfo.getmUserSurname());
//            mPhone.setText(userInfo.getmUserPhoneNumber());
//        }else{
//            mName.setText("Name");
//            mSurname.setText("Surname");
//            mPhone.setText(R.string.edt_masked_custom_mobile_hint);
//        }
//    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.create_img_register_activity):
                writeToDataBase();
                Toast.makeText(UserInfoActivity.this, "User createrd", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserInfoActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void openGallery() {
        Intent galleryAction = new Intent();
        galleryAction.setType("image/*");
        galleryAction.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryAction, "Select Picture"), GALLERY_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPermissionResult(true);
            } else {
                cameraPermissionResult(false);
            }
        }


    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(UserInfoActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);

        } else {
            cameraPermissionResult(true);
        }
    }

    private void cameraPermissionResult(boolean cameraPermissionGranted) {
        if (cameraPermissionGranted) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(UserInfoActivity.this, "CANCEL",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mUserImage.setImageBitmap(photo);


            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
//            imageUri = data.getData();
//
//            final StorageReference myPath = mReference.child("USER BOX").child(imageUri.getLastPathSegment());
//            myPath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                   mProgressDialog.dismiss();
//                }
//            });
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {

            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();

            imageUri = data.getData();
            final StorageReference myPath = mReference.child("USERBOX").child(imageUri.getLastPathSegment() + ".jpg");
            myPath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    myPath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mDataBaseReference.child(user.getUid()).child("mImageUrl").setValue(uri.toString());

                        }
                    });
                }
            });

        }

    }


}
