package com.example.bhargav.paypes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;

public class Main9Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    PullToRefreshView mPullToRefreshView;
    int REFRESH_DELAY=3000;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,myRef1,myRef2,myRef3,myRef5,myRef6,myRef7,myRef8,myRef126, myRef129;
    private FirebaseAuth mAuth;
    ProgressDialog asyncDialog112,pd;
    private TextView amountleft,statu,code;
    private ListView mListView;
    String  string114;
    int flag=0,flag1=0;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Main7activity";
    ArrayList<String> array = new ArrayList<>();
    Button bun,sun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        asyncDialog112=new ProgressDialog(Main9Activity.this);
        pd=new ProgressDialog(Main9Activity.this);
        asyncDialog112.setMessage("loading...");
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        bun=(Button)findViewById(R.id.addmoney);
        sun=(Button)findViewById(R.id.merchant);
        amountleft=(TextView)findViewById(R.id.textView1234);
        statu=(TextView)findViewById(R.id.textView1996);
        code=(TextView)findViewById(R.id.code);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        bun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Main9Activity.this,Main8Activity.class);
                startActivity(intent);
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Main9Activity.this,Main16Activity.class);
                startActivity(intent);

            }
        });



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fime();
                fime2();
                mPullToRefreshView.setRefreshing(false);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //fime4();
                    Boolean kim =user.isEmailVerified();
                    if(kim==true)
                    {
                        toastMessage("sucess verified");
                        mFirebaseDatabase = FirebaseDatabase.getInstance();
                        myRef = mFirebaseDatabase.getReference();
                        myRef1=myRef.child("user");
                        asyncDialog112.show();
                        Log.v(TAG,"hi3");
                        //Intent intent98=new Intent(Main7Activity.this,Main4Activity.class);
                        //startActivity(intent98);
                        ProgressTheard thread=new ProgressTheard();
                        thread.start();



                    }else
                    {
                        toastMessage("not sucess verified");
                        Intent intent = new Intent(Main9Activity.this,Main4Activity.class);
                        startActivity(intent);
                        //asyncDialog112.dismiss();
                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent98=new Intent(Main9Activity.this,Main4Activity.class);
                    startActivity(intent98);
                }
                // ...
            }
        };



    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            mAuth.signOut();
            toastMessage("Signing Out...");
            Intent intent98=new Intent(Main9Activity.this,Main4Activity.class);
            startActivity(intent98);


            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            pd.setMessage("activating account");
            pd.show();
            fime1("1");



        } else if (id == R.id.nav_slideshow) {
            pd.setMessage("deactivating account");
            pd.show();
            fime1("0");

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser user = mAuth.getCurrentUser();
           final Query myRef122=myRef.child("user").orderByKey().equalTo(user.getUid());
            myRef122.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.v(TAG,"hi");
                    /*
                    FirebaseUser user = mAuth.getCurrentUser();
                    String kol=user.getUid();
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        String fan = ds.getKey();
                        Log.v(TAG, fan);
                        if((fan.equals(kol)))
                        {
                            flag=1;
                            break;
                        }

                        //asyncDialog112.dismiss();
                    }*/
                    long i =dataSnapshot.getChildrenCount();
                    String jimmy= Long.toString(i);
                    if(jimmy.equals("1"))
                    {
                        flag=1;
                    }
                    if(flag==1)
                    {
                        toastMessage("Welcome to PayPes");

                        Log.d(TAG, "yes come on");
                        final FirebaseUser user = mAuth.getCurrentUser();
                        myRef7=myRef.child("user").child(user.getUid());
                        myRef7.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String data123=dataSnapshot.getValue(String.class);
                                if(!(data123.startsWith("UG")))
                                {
                                    Intent intent=new Intent(Main9Activity.this,Main11Activity.class);
                                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                                    myRef = mFirebaseDatabase.getReference();
                                    myRef129=myRef.child("notify").child(user.getUid()).child("notificationTokens");
                                    myRef129.removeValue();
                                    myRef.child("notify").child(user.getUid()).child("notificationTokens").child(refreshedToken).setValue("true");

                                    Log.v(TAG,refreshedToken);
                                    startActivity(intent);
                                    asyncDialog112.dismiss();
                                }
                                else
                                {
                                    Log.d(TAG, "yes come on");
                                    toastMessage("heyyy");
                                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                                    myRef = mFirebaseDatabase.getReference();
                                    myRef126=myRef.child("notify").child(user.getUid()).child("notificationTokens");
                                    myRef126.removeValue();

                                    myRef.child("notify").child(user.getUid()).child("notificationTokens").child(refreshedToken).setValue("true");

                                    fime();
                                    fime2();
                                    fime3();
                                    asyncDialog112.dismiss();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }else
                    {
                        Intent intent = new Intent(Main9Activity.this,Main6Activity.class);
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
    private void fime()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mFirebaseDatabase.getReference();
        myRef5=myRef.child("user").child(user.getUid());
        myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string113=dataSnapshot.getValue(String.class);
                Log.v(TAG,string113);
                myRef6=myRef.child("users").child(string113).child("amount");
                myRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        string114=dataSnapshot.getValue(String.class);
                        amountleft.setText(string114);
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
    private void fime1(final String sij)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mFirebaseDatabase.getReference();
        myRef5=myRef.child("user").child(user.getUid());
        myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string113=dataSnapshot.getValue(String.class);
                Log.v(TAG,string113);
                myRef.child("users").child(string113).child("status").setValue(sij);
                pd.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void fime2()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mFirebaseDatabase.getReference();
        myRef5=myRef.child("user").child(user.getUid());
        myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String string113=dataSnapshot.getValue(String.class);
                Log.v(TAG,string113);
                myRef6=myRef.child("users").child(string113).child("status");
                myRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        string114=dataSnapshot.getValue(String.class);
                        if(string114.equals("1"))
                        {
                        statu.setText("active");
                    }else
                        {
                            statu.setText("not active");
                        }
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
    private void fime3()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mFirebaseDatabase.getReference();
        myRef5=myRef.child("user").child(user.getUid());
        myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String string113=dataSnapshot.getValue(String.class);
                Log.v(TAG,string113);
                myRef6=myRef.child("users").child(string113).child("code");
                myRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        string114=dataSnapshot.getValue(String.class);
                            String klo="code :"+string114;
                            code.setText(klo);

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
    private void fime4()
    {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef8=myRef.child("user");
        myRef8.addValueEventListener(new ValueEventListener() {
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
                        flag1=1;
                    }

                    //asyncDialog112.dismiss();
                }
                if(flag1==1)
                {
                    toastMessage("heyyy");
                    Log.d(TAG, "yes come on");
                   myRef7=myRef.child("user").child(user.getUid());
                    myRef7.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String data123=dataSnapshot.getValue(String.class);
                            if(!(data123.startsWith("UG")))
                            {
                                Intent intent=new Intent(Main9Activity.this,Main11Activity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //asyncDialog112.dismiss();
                }else
                {
                    toastMessage("sorry dude");
                    //asyncDialog112.dismiss();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
