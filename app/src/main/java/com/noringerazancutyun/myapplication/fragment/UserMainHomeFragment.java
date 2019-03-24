package com.noringerazancutyun.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.activity.AddActivity;
import com.noringerazancutyun.myapplication.activity.HomeActivity;
import com.noringerazancutyun.myapplication.activity.MyStat;
import com.noringerazancutyun.myapplication.activity.UserInfoActivity;
import com.noringerazancutyun.myapplication.models.UserInform;
import com.noringerazancutyun.myapplication.util.MyFirebase;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;


public class UserMainHomeFragment extends Fragment {

    private static final int REQUEST_FOR_LOGIN = 10;

    FloatingActionButton mAddStatement;
    TextView mProfile, mNotification, mHistory, mStatement, mLogout, mUserName;
    MyFirebase firebase = new MyFirebase();
    UserInform user = new UserInform();
    private DatabaseReference mDataBaseReference;
    String userID;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    CircleImageView  mUserProfileImage;
    String nameSurname;


    public UserMainHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_main_home, container, false);

        mAddStatement = view.findViewById(R.id.add_statement_button);
        mProfile = view.findViewById(R.id.profile_txt);
        mNotification = view.findViewById(R.id.notification_txt);
        mHistory = view.findViewById(R.id.history_txt);
        mUserProfileImage = view.findViewById(R.id.user_profie_image);
        mStatement = view.findViewById(R.id.statement_txt);
        mLogout = view.findViewById(R.id.logout_txt);
        mUserName = view.findViewById(R.id.user_name_surmname_txt);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        mDataBaseReference = FirebaseDatabase.getInstance().getReference("User");
        userID = firebaseUser.getUid();

        mAddStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });

        readFromDB();

        clickLogoutAction();
        clickProfileAction();
        clickStatementAction();
        return view;
    }

    public void clickProfileAction() {
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
             startActivityForResult(intent, REQUEST_FOR_LOGIN);
            }
        });
    }


    public void clickNotificAction() {
        mNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clickhistoryAction() {
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clickStatementAction() {
        mStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyStat.class);
                startActivity(intent);
            }
        });
    }

    public void clickLogoutAction() {
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                firebase.mAuth.signOut();
                firebase.mAuth.addAuthStateListener(firebase.listener);
                startActivity(intent);
            }
        });
    }

    private void readFromDB() {

        if(mDataBaseReference != null)
        mDataBaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserInform.class);
                nameSurname = (user.getmUserName() + "  " + user.getmUserSurname());
                mUserName.setText(nameSurname);
                userSetImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        else nameSurname = firebaseUser.getEmail();
    }

    private void userSetImage() {

        Glide.with(getContext())
                .load(user.getmImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.avatar_icon)
                .fitCenter()
                .into(mUserProfileImage);
    }


}
