package com.noringerazancutyun.myapplication.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.noringerazancutyun.myapplication.R;
import com.noringerazancutyun.myapplication.models.UserInform;
import com.noringerazancutyun.myapplication.util.MyFirebase;


public class EmailPasswordActivity extends BaseActivity implements
        View.OnClickListener {

    MyFirebase firebase = new MyFirebase();

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;
    private ImageView mLoginImg, mCreateImg;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_password);

        mEmailField = findViewById(R.id.email_text);
        mPasswordField = findViewById(R.id.password_text);
        mLoginImg = findViewById(R.id.login_img_email_activity);
        mCreateImg = findViewById(R.id.create_img_email_activity);
        mLoginImg.setOnClickListener(this);
        mCreateImg.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.mAuth.addAuthStateListener(firebase.listener);

    }

    private void createAccount(final String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        firebase.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebase.mAuth.getCurrentUser();
                            Intent intent = new Intent (EmailPasswordActivity.this, UserInfoActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        firebase.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebase.mAuth.getCurrentUser();
                            Intent intent = new Intent (EmailPasswordActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_img_email_activity) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.login_img_email_activity) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.mAuth.addAuthStateListener(firebase.listener);
    }
}

