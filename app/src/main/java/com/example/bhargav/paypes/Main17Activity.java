package com.example.bhargav.paypes;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Main17Activity extends AppCompatActivity {

    private ListView mListView;
    ProgressDialog asyncDialog112;
    String user101;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef,myRef2,myRef3;
    ArrayList<String> array  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main17);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();
        mListView = (ListView) findViewById(R.id.list_view2);
        asyncDialog112=new ProgressDialog(Main17Activity.this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        // myRef2= myRef.child("orders");
        asyncDialog112.setMessage("loading details");
        asyncDialog112.show();
        ProgressTheard theard=new ProgressTheard();
        theard.start();
    }
    private class ProgressTheard extends Thread{
        public void run() {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            myRef3=myRef.child("user").child(user.getUid());
            myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user101=dataSnapshot.getValue(String.class);
                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    myRef = mFirebaseDatabase.getReference();
                    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    myRef2= myRef.child("orders");
                    myRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                                Map<String, String> map = ds.getValue(genericTypeIndicator );
                                String fun=map.get("amount");
                                String hum=map.get("user");
                                String jim=map.get("date");
                                String jill=map.get("merchant");
                                //Log.v("E_VALU", fun);
                                //String user101=user10.getText().toString();
                                if(jill.equals(user101)) {
                                    Log.v("hi", fun);

                                    String fan =fun+" | "+hum+" | "+jim;
                                    array.add(fan);
                                    //ArrayAdapter adapter = new ArrayAdapter(Main17Activity.this,android.R.layout.simple_list_item_1,array);
                                    CustomListAdapter listAdapter = new CustomListAdapter(Main17Activity.this , R.layout.custom_list , array);
                                    mListView.setAdapter(listAdapter);

                                }

                            }
                            toastMessage("done");
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
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

