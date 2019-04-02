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
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import com.noringerazancutyun.myapplication.models.Statement;
import com.noringerazancutyun.myapplication.models.UserInform;
import com.noringerazancutyun.myapplication.util.MyFirebase;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final String TAG = "UserInfoActivity";


    private EditText mName, mSurname, mPhone;
    private ImageView mSaveButton;
    private CircleImageView mUserImage;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    private DatabaseReference mDataBaseReference;
    private StorageReference mReference;
    private String userImage, userID, name, surname, phone, image;
    UserInform userInfo;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();
        mName = findViewById(R.id.user_name);
        mSurname = findViewById(R.id.user_surname);
        mPhone = findViewById(R.id.user_phone);
        mUserImage = findViewById(R.id.user_image);
        mSaveButton = findViewById(R.id.create_img_register_activity);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        mReference = FirebaseStorage.getInstance().getReference();

        userID =user.getUid();
        mSaveButton.setOnClickListener(this);
        mUserImage.setOnClickListener(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        if(name!= null){
            checkUser();
        }



    }


    private void writeToDataBase() {

         name = mName.getText().toString();
         surname = mSurname.getText().toString();
         phone = mPhone.getText().toString();
        if (image == null) {
            image = "no photo";
        }
        userInfo = new UserInform(name, surname, phone, image);
        mDataBaseReference.child("User").child(userID).setValue(userInfo);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.create_img_register_activity):

                if (validateForm()) {
                    writeToDataBase();
                    Toast.makeText(UserInfoActivity.this, "User createrd", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserInfoActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                break;
            case (R.id.user_image):
                openGallery();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {



            imageUri = data.getData();
            final StorageReference myPath = mReference.child("USERBOX").child(imageUri.getLastPathSegment() + ".jpg");
            myPath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    myPath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image = uri.toString();
                        }
                    });
                }
            });

        }

    }


    private boolean validateForm() {
        boolean valid = true;


        String name = mName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mName.setError("Required.");
            valid = false;
        } else {
            mName.setError(null);
        }


        String surname = mSurname.getText().toString();
        if (TextUtils.isEmpty(surname)) {
            mSurname.setError("Required.");
            valid = false;
        } else {
            mSurname.setError(null);
        }

        String number = mPhone.getText().toString();
        if (TextUtils.isEmpty(number)) {
            mPhone.setError("Required.");
            valid = false;
        } else {
            mPhone.setError(null);
        }

        return valid;
    }

    private void checkUser(){

        Intent intent = getIntent();
        surname = intent.getStringExtra("surname");
        phone = intent.getStringExtra("phone");
        image = intent.getStringExtra("imageUrl");
        mName.setText(name);
        mSurname.setText(surname);
        mPhone.setText(phone);
        Glide.with(this)
                .load(image)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.avatar_icon)
                .fitCenter()

                .into(mUserImage);

    }


}
