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

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Main6Activity extends AppCompatActivity {
Button go,barcode;
    EditText mobile,name;
    int flag=1;
    TextView scannedbarcode;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    String loin,poin;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,myRef1,myRef2,myRef3;
    private FirebaseAuth mAuth;
    ProgressDialog asyncDialog11;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String dawn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();
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
        go=(Button)findViewById(R.id.go2);
        barcode=(Button)findViewById(R.id.scanbarcode);
        mobile=(EditText)findViewById(R.id.mobile);
        name=(EditText)findViewById(R.id.name1);
        scannedbarcode=(TextView)findViewById(R.id.barcode);
        scannedbarcode.getText().toString();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        asyncDialog11 = new ProgressDialog(   Main6Activity.this);
        asyncDialog11.setMessage("creating...");
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main6Activity.this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncDialog11.show();
                 loin =name.getText().toString();
                poin= mobile.getText().toString();
              if(  loin != null && poin!=null )
              {
                  dawn=scannedbarcode.getText().toString();
                  if(!dawn.startsWith("barcode") )
                  {     asyncDialog11.show();
                      ProgressTheard thread=new ProgressTheard();
                      thread.start();


                  }else
                  {
                   toastMessage("scan barcode");
                      asyncDialog11.dismiss();
                  }

              }else
              {
                  toastMessage("enter name and mobile number");
                  asyncDialog11.dismiss();

              }


            }
        });



    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    // statusMessage.setText(R.string.barcode_success);
                    scannedbarcode=(TextView)findViewById(R.id.barcode);
                    String mava=barcode.displayValue;
                    if(mava.startsWith("UG")) {
                        scannedbarcode.setText(barcode.displayValue);

                    }
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    // statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                //      CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private class ProgressTheard extends Thread{
        public void run() {

            myRef2=myRef.child("users");
            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String fan = ds.getKey();
                        Log.v("hi", fan);
                        if(fan.equals(dawn))
                        {
                            flag=0;
                        }
                       // array.add(fan);

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if (flag==1) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                myRef.child("user").child(user.getUid()).setValue(dawn);
                myRef.child("users").child(dawn).child("name").setValue(loin);
                myRef.child("users").child(dawn).child("mobile").setValue(poin);
                Random rand = new Random();
                String id = String.format("%04d", rand.nextInt(10000));
                myRef.child("users").child(dawn).child("code").setValue(id);
                myRef.child("users").child(dawn).child("status").setValue("1");
                myRef.child("users").child(dawn).child("amount").setValue("0");
                String loke = user.getEmail();
                myRef.child("users").child(dawn).child("email").setValue(loke);
                Intent intent34 = new Intent(Main6Activity.this, Main9Activity.class);
                startActivity(intent34);
                asyncDialog11.dismiss();
            }
            else
            {
                toastMessage("you have been enrolled with other mail id");
                asyncDialog11.dismiss();
            }


        }
        }
}
