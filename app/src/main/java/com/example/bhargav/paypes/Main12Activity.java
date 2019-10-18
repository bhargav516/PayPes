package com.example.bhargav.paypes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class Main12Activity extends AppCompatActivity {
    TextView bar;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        Button go123=(Button)findViewById(R.id.go1234);
        Button sacnbar=(Button)findViewById(R.id.scanbarcode1234);
        bar=(TextView) findViewById(R.id.barcode1234);
        android.support.v7.app.ActionBar AB=getSupportActionBar();

        AB.hide();

        final String data = getIntent().getExtras().getString("keyName");
        go123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dawn=bar.getText().toString();
                if(!dawn.startsWith("Barcode") )
                {
                    Intent intent=new Intent(Main12Activity.this,Main13Activity.class);
                    intent.putExtra("keyName",data);
                    intent.putExtra("keyName1",bar.getText());
                    startActivity(intent);


                }else
                {
                    toastMessage("scan barcode");

                }

            }
        });
        sacnbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main12Activity.this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    // statusMessage.setText(R.string.barcode_success);
                    //scannedbarcode=(TextView)findViewById(R.id.barcode);
                    bar=(TextView) findViewById(R.id.barcode1234);
                    String mava=barcode.displayValue;
                    if(mava.startsWith("UG")) {
                        bar.setText(barcode.displayValue);

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
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
