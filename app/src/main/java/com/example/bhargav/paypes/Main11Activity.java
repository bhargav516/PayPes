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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yalantis.phoenix.PullToRefreshView;

public class Main11Activity extends AppCompatActivity {
PullToRefreshView mPullToRefreshView;
    Button logout,transaction;
    int REFRESH_DELAY=3000;
    String   string117,string110;
    private static final String TAG = "Main10activity";
    private EditText emailEditText;
    private TextView passEditText;
    private TextView createone;
    private FirebaseAuth mAuth;
    ProgressDialog asyncDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,myRef1,myRef2,myRef3,myRef5,myRef6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        Button getmoney=(Button)findViewById(R.id.getmoney);
        logout =(Button)findViewById(R.id.logout123);
        passEditText=(TextView)findViewById(R.id.amount123);
        createone=(TextView)findViewById(R.id.thismerchant123);
        asyncDialog=new ProgressDialog(Main11Activity.this);
        asyncDialog.setMessage("loading...");
        transaction=(Button)findViewById(R.id.transaction123);
        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        asyncDialog.show();
       ProgressTheard thread=new ProgressTheard();
        thread.start();
        getmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main11Activity.this,Main14Activity.class);
                startActivity(intent);
            }
        });
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main11Activity.this,Main17Activity.class);
                startActivity(intent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                toastMessage("Signing Out...");
                Intent intent=new Intent(Main11Activity.this,Main4Activity.class);
                startActivity(intent);

            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private class ProgressTheard extends Thread{
        public void run() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
           // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            myRef = mFirebaseDatabase.getReference();
            myRef5=myRef.child("user").child(user.getUid());
            myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String string113=dataSnapshot.getValue(String.class);
                    Log.v(TAG,string113);
                    myRef6=myRef.child("merchant").child(string113).child("amount");
                    myRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            string117=dataSnapshot.getValue(String.class);
                            passEditText.setText(string117);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    myRef2=myRef.child("merchant").child(string113).child("name");
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            string110=dataSnapshot.getValue(String.class);
                            createone.setText(string110);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    asyncDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        }
}
