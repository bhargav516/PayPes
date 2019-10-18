package com.example.bhargav.paypes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class Main8Activity extends AppCompatActivity implements PaymentResultListener {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,myRef1,myRef11,myRef2,myRef3,myRef21;
    private FirebaseAuth mAuth;
    private EditText editText;
    ProgressDialog asyncDialog112,asyncDialog113;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String string111,string112,string113,string114,string115,string1112;
    private static final String TAG = "Main8activity";
    private Button addmoney;
    private TaskCompletionSource<String> dbSource = new TaskCompletionSource<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        mAuth = FirebaseAuth.getInstance();
        editText=(EditText)findViewById(R.id.amount111);
        addmoney=(Button)findViewById(R.id.addmoney);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        asyncDialog112 = new ProgressDialog(   Main8Activity.this);
        asyncDialog112.setMessage("adding money to wallet");
        asyncDialog113 = new ProgressDialog(   Main8Activity.this);
        asyncDialog113.setMessage("loading..");
        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();

        Checkout.preload(getApplicationContext());
        asyncDialog113.show();
        ProgressTheard2 thread2=new ProgressTheard2();
        thread2.start();
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
            }
        };
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        editText.setText("");
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string=editText.getText().toString();
                if(string.equals(""))
                {
                    editText.setError("Enter Amount");
                }else {


                    startPayment();
                }
            }
        });
    }
    private void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();
        final String string=editText.getText().toString();
        int  result1 = Integer.parseInt(string);
        result1=result1*100;
        Log.v(TAG,"safe");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String numberAsString1 = new Integer(result1).toString();
        String loke = user.getEmail();



        try{
            JSONObject options=new JSONObject();
            options.put("name", "PayPes");
            options.put("description","add money to wallet");
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", numberAsString1);
            JSONObject preFill = new JSONObject();
            preFill.put("email", loke);
            preFill.put("contact", string112);
            options.put("prefill", preFill);

            co.open(activity, options);



        } catch (JSONException e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }


    }

    @Override
    public void onPaymentSuccess(String s) {
        try {
            Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
        asyncDialog112.show();
        ProgressTheard thread=new ProgressTheard();
        thread.start();
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }

    }
    private class ProgressTheard extends Thread{
        public void run() {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            myRef11=myRef.child("user").child(user.getUid());
            myRef11.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    string113=dataSnapshot.getValue(String.class);
                    Log.v(TAG,string113);
                    myRef3=myRef.child("users").child(string113).child("amount");
                    myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            string114=dataSnapshot.getValue(String.class);
                            Log.v(TAG,string114+string113);
                            string115=editText.getText().toString();
                            Double amountgot = Double.parseDouble(string114);
                            Double amountadded=Double.parseDouble(string115);
                            Double totalamount=amountgot+amountadded;
                            String numberAsString2 = new Double(totalamount).toString();
                            myRef.child("users").child(string113).child("amount").setValue(numberAsString2);
                            mFirebaseDatabase = FirebaseDatabase.getInstance();
                            myRef = mFirebaseDatabase.getReference();
                            myRef21=myRef.child("orders1");
                            myRef21.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    string1112=dataSnapshot.getValue(String.class);
                                    int result10 = Integer.parseInt(string1112);
                                    result10 = result10 + 1;
                                    String numberAsString10 = new Integer(result10).toString();
                                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                    myRef.child("orders1").setValue(numberAsString10);
                                    myRef.child("orders").child(numberAsString10).child("merchant").setValue("self");
                                    myRef.child("orders").child(numberAsString10).child("user").setValue(string113);
                                    myRef.child("orders").child(numberAsString10).child("amount").setValue(string115);
                                    myRef.child("orders").child(numberAsString10).child("date").setValue(currentDateTimeString);
                                    Log.v(TAG, numberAsString10);
                                    asyncDialog112.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

        }
    private class ProgressTheard2 extends Thread {
        public void run() {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            myRef1=myRef.child("user").child(user.getUid());
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    string111=dataSnapshot.getValue(String.class);
                    Log.v(TAG,string111);
                    myRef2=myRef.child("users").child(string111).child("mobile");
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //string112=dataSnapshot.getValue(String.class);
                            Log.v(TAG,dataSnapshot.toString());
                            dbSource.setResult(dataSnapshot.getValue(String.class));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Task<String> dbTask = dbSource.getTask();
            dbTask.addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    Log.v("hi", task.getResult());
                    string112=task.getResult();
                    Log.v(TAG,string112);
                    asyncDialog113.dismiss();
                }
            });

        }
    }
}
