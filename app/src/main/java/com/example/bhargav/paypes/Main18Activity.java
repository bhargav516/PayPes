package com.example.bhargav.paypes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main18Activity extends AppCompatActivity {
    EditText name,mobile,email,bank,perpose;
    String jillname,jillmobile,jillemail,jillbank,jillperpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main18);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();
       name =(EditText)findViewById(R.id.namebar);
           mobile     =(EditText)findViewById(R.id.mobilebar);

                email=(EditText)findViewById(R.id.emailbar);

                bank=(EditText)findViewById(R.id.accountnumberbar);
                perpose=(EditText)findViewById(R.id.perposebar);
        Button gone=(Button)findViewById(R.id.gobar);
        gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jillname=name.getText().toString();
                jillmobile=mobile.getText().toString();
                jillemail=email.getText().toString();
                jillbank=bank.getText().toString();
                jillperpose=perpose.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + "bhargav19968@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Add as merchant");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "name :" +jillname+"\n"+"mobile :"+jillmobile+"\n"+"bank account :"+jillbank+"\n"+"ifsc code :"+jillemail+"\n"+"perpose :"+jillperpose);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Main18Activity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
