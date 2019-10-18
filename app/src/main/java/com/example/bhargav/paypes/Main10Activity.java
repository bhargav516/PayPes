package com.example.bhargav.paypes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhargav.paypes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main10Activity extends AppCompatActivity {
    private static final String TAG = "Main10activity";
    private EditText emailEditText;
    private EditText passEditText;
    private TextView createone;
    private FirebaseAuth mAuth;
     String email,pass;
    ProgressDialog asyncDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,myRef1,myRef2,myRef3,myRef5,myRef6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mAuth = FirebaseAuth.getInstance();
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
        asyncDialog = new ProgressDialog(   Main10Activity.this);
        asyncDialog.setMessage("Loading..");

        emailEditText = (EditText) findViewById(R.id.email123);
        passEditText = (EditText) findViewById(R.id.password123);
        createone=(TextView)findViewById(R.id.whattobemerchant);
        createone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(Main10Activity.this,Main18Activity.class);
               startActivity(intent);

            }
        });

    }
    public void checkLogin(View arg0) {

         email = emailEditText.getText().toString();
        if (!isValidEmail(email)) {
            //Set error message for email field
            emailEditText.setError("Invalid Email");
        }

         pass = passEditText.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            passEditText.setError("Password cannot be empty");
        }

        if(isValidEmail(email) && isValidPassword(pass))
        {
            // Validation Completed
            asyncDialog.show();
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                toastMessage("not succesfull please login again");
                                asyncDialog.dismiss();
                            }else
                            {
                                toastMessage("succesfull");
                                ProgressTheard thread=new ProgressTheard();
                                thread.start();

                            }

                            // ...
                        }
                    });

        }

    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
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
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private class ProgressTheard extends Thread{
        public void run() {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            myRef1=myRef.child("user").child(user.getUid());
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String rocky=dataSnapshot.getValue(String.class);
                    if(rocky.startsWith("UG"))
                    {
                        toastMessage("sorry you are not merchant");
                        asyncDialog.dismiss();
                    }
                   else
                    {
                       // mFirebaseDatabase = FirebaseDatabase.getInstance();
                        //myRef = mFirebaseDatabase.getReference();
                        Log.v(TAG,"i am here");
                        mAuth.signInWithEmailAndPassword(email, pass);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Boolean kim =user.isEmailVerified();
                        Log.v(TAG,kim.toString());
                        if(kim==true)
                        {
                            toastMessage("sucess verified");
                            Intent intent=new Intent(Main10Activity.this,Main11Activity.class);
                            startActivity(intent);
                            asyncDialog.dismiss();

                        }else
                        {
                            toastMessage("not sucess verified");

                            Intent intent = new Intent(Main10Activity.this,Main15Activity.class);
                            intent.putExtra("keyName",email);
                            intent.putExtra("keyName1",pass);
                            Log.v(TAG, email + pass);
                            startActivity(intent);
                            asyncDialog.dismiss();
                        }



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        }
}