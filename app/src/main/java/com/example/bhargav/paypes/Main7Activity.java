package com.example.bhargav.paypes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Main7Activity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,myRef1,myRef2,myRef3;
    private FirebaseAuth mAuth;
    ProgressDialog asyncDialog112;
    private ListView mListView;
    int flag=0;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Main7activity";
    ArrayList<String> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
       // mListView = (ListView) findViewById(R.id.listview);
        mAuth = FirebaseAuth.getInstance();
        asyncDialog112=new ProgressDialog(Main7Activity.this);
        asyncDialog112.setMessage("hiii");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    Boolean kim =user.isEmailVerified();
                    if(kim==true)
                    {
                        toastMessage("sucess verified");
                        Log.v(TAG,"hi2");
                        mFirebaseDatabase = FirebaseDatabase.getInstance();
                        myRef = mFirebaseDatabase.getReference();
                        myRef2=myRef.child("user");
                        asyncDialog112.show();
                        Log.v(TAG,"hi3");
                        //Intent intent98=new Intent(Main7Activity.this,Main4Activity.class);
                        //startActivity(intent98);
                        ProgressTheard thread=new ProgressTheard();
                        thread.start();






                    }else
                    {
                        toastMessage("not sucess verified");
                        Intent intent = new Intent(Main7Activity.this,Main5Activity.class);
                        startActivity(intent);
                        //asyncDialog112.dismiss();
                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent98=new Intent(Main7Activity.this,Main4Activity.class);
                    startActivity(intent98);
                }
                // ...
            }
        };

    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private class ProgressTheard extends Thread{
        public void run() {
            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v(TAG,"hi");
                    FirebaseUser user = mAuth.getCurrentUser();
                    String kol=user.getUid();
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        String fan = ds.getKey();
                        Log.v(TAG, fan);
                        if((fan.equals(kol)))
                        {
                            flag=1;
                        }

                        asyncDialog112.dismiss();
                    }
                    if(flag==1)
                    {
                        toastMessage("hi");
                        Log.d(TAG, "yes come on");
                        asyncDialog112.dismiss();
                    }else
                    {
                        Intent intent = new Intent(Main7Activity.this,Main6Activity.class);
                        startActivity(intent);
                        asyncDialog112.dismiss();
                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        }

        }
