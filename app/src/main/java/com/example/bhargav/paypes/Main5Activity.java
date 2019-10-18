package com.example.bhargav.paypes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main5Activity extends AppCompatActivity {

private TextView mverified,mverified1;
    private Button verified,verified1;
    private FirebaseAuth mAuth;
    private static final String TAG = "Main5Activity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog asyncDialog3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        asyncDialog3 = new ProgressDialog(   Main5Activity.this);
        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();
        verified =(Button)findViewById(R.id.verify);
        mverified=(TextView) findViewById(R.id.verified);
        verified1 =(Button)findViewById(R.id.verify11);
        mverified1=(TextView) findViewById(R.id.verified11);
        final String data = getIntent().getExtras().getString("keyName");
        final String data2 = getIntent().getExtras().getString("keyName1");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.v(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.v(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.v(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                asyncDialog3.setMessage("sending mail for verification");
                asyncDialog3.show();
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    toastMessage("Email sent");
                                    asyncDialog3.dismiss();
                                }else
                                {
                                    toastMessage("check internet connection or email");
                                    asyncDialog3.dismiss();
                                }
                            }
                        });




            }
        });
        verified1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mAuth.signInWithEmailAndPassword(data, data2);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Boolean lon = user.isEmailVerified();
                asyncDialog3.setMessage("verifing");
                asyncDialog3.show();
                Log.v(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                // if (lon == false) {
                Log.v(TAG,"email verfied"+lon);
                if (lon==true)
                {
                    toastMessage(" verified");
                    mverified.setText("verified");
                    Intent intent = new Intent(Main5Activity.this, Main6Activity.class);
                    startActivity(intent);
                    asyncDialog3.dismiss();
                }else
                {
                    toastMessage(" click on verify again");
                    asyncDialog3.dismiss();
                }

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

           Boolean kiml = user.isEmailVerified();
        if (kiml == true) {
               toastMessage("sucess verified");
                mverified.setText("verified");
                Intent intent = new Intent(Main5Activity.this, Main6Activity.class);
                startActivity(intent);
            }else
        {
            Log.v(TAG,"email verfied"+kiml);

        }
        //user.isEmailVerified().addO


    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
