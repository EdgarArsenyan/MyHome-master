package com.noringerazancutyun.myapplication.util;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

public class MyFirebase {

public FirebaseAuth mAuth= FirebaseAuth.getInstance();
 public FirebaseAuth.AuthStateListener listener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


        }
    };

}
