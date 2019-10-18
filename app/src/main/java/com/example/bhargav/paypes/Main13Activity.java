package com.example.bhargav.paypes;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class Main13Activity extends Activity {

    private final String SDK_VERSION = "1";
    private final int MENUITEM_CLOSE = 300;
    private FirebaseAuth mAuth;
    String  string114;
    String pungi;
    int flag=0,flag1=0;

    private static final String TAG = "Main7activity";
    ProgressDialog asyncDialog112,pd;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    ProgressDialog asyncDialog;
    private DatabaseReference myRef,myRef1,myRef2,myRef3,myRef4,myRef5,myRef6,myRef20;
    String amount,usn ;
    String kill;
    /*
     * Edit Text and Button object initialization for simple calculator design.
     */
    private EditText txtCalc = null;
    private Button btnZero = null;
    private Button btnOne = null;
    private Button btnTwo = null;
    private Button btnThree = null;
    private Button btnFour = null;
    private Button btnFive = null;
    private Button btnSix = null;
    private Button btnSeven = null;
    private Button btnEight = null;
    private Button btnNine = null;
    private Button btnPlus = null;
    private Button btnMinus = null;
    private Button btnMultiply = null;
    private Button btnDivide = null;
    private Button btnEquals = null;
    private Button btnC = null;
    private Button btnDecimal = null;

    private Button btnPerc = null;
    private Button btnSqrRoot = null;
    private Button btnPM = null;


    private double num = 0;
    private double memNum = 0;
    private int operator = 1;
    // 0 = nothing, 1 = plus, 2 = minus, 3 =
    // multiply, 4 = divide
    private boolean readyToClear = false;
    private boolean hasChanged = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Black);
        setContentView(R.layout.activity_main13);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        amount = getIntent().getExtras().getString("keyName");
         usn = getIntent().getExtras().getString("keyName1");
        toastMessage(amount);
        kon();
        ProgressTheard();
        toastMessage(usn);
        asyncDialog = new ProgressDialog(   Main13Activity.this);
        asyncDialog.setMessage("Loading..");
        this.setTitle(" ");

        initControls();
        initScreenLayout();
        reset();

    }

    private void initScreenLayout() {

		/*
		 * The following three command lines you can use depending upon the
		 * emulator device you are using.
		 */

        // 320 x 480 (Tall Display - HVGA-P) [default]
        // 320 x 240 (Short Display - QVGA-L)
        // 240 x 320 (Short Display - QVGA-P)

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // this.showAlert(dm.widthPixels +" "+ dm.heightPixels, dm.widthPixels
        // +" "+ dm.heightPixels, dm.widthPixels +" "+ dm.heightPixels, false);

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        if (height < 400 || width < 300) {
            txtCalc.setTextSize(20);
        }

        if (width < 300) {


            btnPM.setTextSize(18);

            btnNine.setTextSize(18);
            btnEight.setTextSize(18);
            btnSeven.setTextSize(18);
            btnSix.setTextSize(18);
            btnFive.setTextSize(18);
            btnFour.setTextSize(18);
            btnThree.setTextSize(18);
            btnTwo.setTextSize(18);
            btnOne.setTextSize(18);
            btnZero.setTextSize(18);
            btnDecimal.setTextSize(18);

        }

        txtCalc.setTextColor(Color.WHITE);
        txtCalc.setBackgroundColor(Color.BLACK);
        txtCalc.setKeyListener(null);

        btnZero.setTextColor(Color.WHITE);
        btnOne.setTextColor(Color.WHITE);
        btnTwo.setTextColor(Color.WHITE);
        btnThree.setTextColor(Color.WHITE);
        btnFour.setTextColor(Color.WHITE);
        btnFive.setTextColor(Color.WHITE);
        btnSix.setTextColor(Color.WHITE);
        btnSeven.setTextColor(Color.WHITE);
        btnEight.setTextColor(Color.WHITE);
        btnNine.setTextColor(Color.WHITE);
        btnPM.setTextColor(Color.WHITE);
        btnDecimal.setTextColor(Color.WHITE);





    }

    private void initControls() {
        txtCalc = (EditText) findViewById(R.id.txtCalc);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);

        btnDecimal = (Button) findViewById(R.id.btnDecimal);


        btnPM = (Button) findViewById(R.id.btnPM);







        btnZero.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(0);

            }

        });

        btnOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(1);
            }
        });
        btnTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(2);
            }
        });
        btnThree.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(3);
            }
        });
        btnFour.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(4);
            }
        });
        btnFive.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(5);
            }
        });
        btnSix.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(6);
            }
        });
        btnSeven.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(7);
            }
        });
        btnEight.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(8);
            }
        });
        btnNine.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(9);
            }
        });


        btnDecimal.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                txtCalc.setText("0");
            }
        });
        btnPM.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String jimmy=txtCalc.getText().toString();
                int i=jimmy.length();
                    if(jimmy.equals("0") )
                    {
                        toastMessage("enter the amount");

                    }else if(i==4) {

                        toastMessage(txtCalc.getText().toString());
                        Log.v(TAG,"i am here");
                       // ProgressTheard2 thread=new ProgressTheard2();
                        //thread.start();
                        {

                            if (flag == 1) {
                                toastMessage("Welcome to PayPes");
                                Log.d(TAG, "yes come on");
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                mFirebaseDatabase = FirebaseDatabase.getInstance();
                                myRef = mFirebaseDatabase.getReference();
                                Log.v(TAG, usn);
                                myRef2 = myRef.child("users").child(usn).child("status");
                                myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String king = dataSnapshot.getValue(String.class);
                                        Log.v(TAG, king);
                                        if (king.equals("1")) {
                                            toastMessage("your account is active");
                                            myRef3 = myRef.child("users").child(usn).child("amount");
                                            myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String longi = dataSnapshot.getValue(String.class);
                                                    Log.v(TAG, longi);
                                                    Double result = Double.parseDouble(longi);
                                                    Log.v(TAG,amount);
                                                    Double result4 = Double.parseDouble(amount);
                                                    //String numberAsString = new Integer(result).toString();
                                                    //Log.v(TAG, numberAsString);
                                                    Double result5 = result - result4;
                                                    String numberAsString2 = new Double(result5).toString();
                                                    // myRef1.child("amount").setValue(numberAsString2);
                                                    Log.v(TAG, numberAsString2);
                                                   mFirebaseDatabase = FirebaseDatabase.getInstance();
                                                    myRef = mFirebaseDatabase.getReference();
                                                    myRef.child("users").child(usn).child("amount").setValue(numberAsString2);
                                                    Log.v(TAG, "done");
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                            Log.v(TAG, "kim" + pungi);
                                            {
                                                mFirebaseDatabase = FirebaseDatabase.getInstance();
                                                myRef = mFirebaseDatabase.getReference();

                                            myRef5 = myRef.child("merchant").child(pungi).child("amount");
                                            myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String lungi = dataSnapshot.getValue(String.class);
                                                    Log.v(TAG, lungi);
                                                    Double result1 = Double.parseDouble(lungi);
                                                    Log.v(TAG, lungi);
                                                    Double result4 = Double.parseDouble(amount);
                                                    Double result3 = result1 + result4;


                                                    String numberAsString1 = new Double(result3).toString();

                                                    myRef.child("merchant").child(pungi).child("amount").setValue(numberAsString1);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                            {
                                                mFirebaseDatabase = FirebaseDatabase.getInstance();
                                                myRef = mFirebaseDatabase.getReference();
                                                myRef6 = myRef.child("orders1");
                                                myRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String man = dataSnapshot.getValue(String.class);
                                                        Log.v(TAG, man);
                                                        int result10 = Integer.parseInt(man);
                                                        result10 = result10 + 1;
                                                        String numberAsString10 = new Integer(result10).toString();
                                                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                                        myRef.child("orders1").setValue(numberAsString10);
                                                        myRef.child("orders").child(numberAsString10).child("merchant").setValue(pungi);
                                                        myRef.child("orders").child(numberAsString10).child("user").setValue(usn);
                                                        myRef.child("orders").child(numberAsString10).child("amount").setValue(amount);
                                                        myRef.child("orders").child(numberAsString10).child("date").setValue(currentDateTimeString);
                                                        Log.v(TAG, numberAsString10);
                                                        mFirebaseDatabase = FirebaseDatabase.getInstance();
                                                        myRef = mFirebaseDatabase.getReference();
                                                        myRef20=myRef.child("user");
                                                        Query myRef13= myRef20.orderByValue().equalTo(usn);
                                                        Log.v("tommy","iam here");
                                                        myRef13.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                //GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                                                                //};
                                                                //Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);
                                                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                                                    kill = dataSnapshot1.getKey().toString();
                                                                    Log.v("tommy", kill);
                                                                }
                                                                mFirebaseDatabase = FirebaseDatabase.getInstance();
                                                                myRef = mFirebaseDatabase.getReference();
                                                                myRef.child("followers").child(kill).child(user.getUid()).setValue(amount+" has been debited from your account at"+pungi);
                                                                Intent intent=new Intent(Main13Activity.this,Main11Activity.class);
                                                                startActivity(intent);
                                                                asyncDialog.dismiss();

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

                                        } else {
                                            toastMessage("your account is not active");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                //asyncDialog112.dismiss();
                            } else {
                                toastMessage("no account PayPes");
                                //asyncDialog112.dismiss();
                            }
                        }

                    }
                }



        });



        txtCalc.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int i, android.view.KeyEvent e) {
                if (e.getAction() == KeyEvent.ACTION_DOWN) {
                    int keyCode = e.getKeyCode();

                    // txtCalc.append("["+Integer.toString(keyCode)+"]");

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_0:
                            handleNumber(0);
                            break;

                        case KeyEvent.KEYCODE_1:
                            handleNumber(1);
                            break;

                        case KeyEvent.KEYCODE_2:
                            handleNumber(2);
                            break;

                        case KeyEvent.KEYCODE_3:
                            handleNumber(3);
                            break;

                        case KeyEvent.KEYCODE_4:
                            handleNumber(4);
                            break;

                        case KeyEvent.KEYCODE_5:
                            handleNumber(5);
                            break;

                        case KeyEvent.KEYCODE_6:
                            handleNumber(6);
                            break;

                        case KeyEvent.KEYCODE_7:
                            handleNumber(7);
                            break;

                        case KeyEvent.KEYCODE_8:
                            handleNumber(8);
                            break;

                        case KeyEvent.KEYCODE_9:
                            handleNumber(9);
                            break;

                        case 43:
                            handleEquals(1);
                            break;

                        case KeyEvent.KEYCODE_EQUALS:
                            handleEquals(0);
                            break;

                        case KeyEvent.KEYCODE_MINUS:
                            handleEquals(2);
                            break;

                        case KeyEvent.KEYCODE_PERIOD:
                            handleDecimal();
                            break;

                        case KeyEvent.KEYCODE_C:
                            reset();
                            break;

                        case KeyEvent.KEYCODE_SLASH:
                            handleEquals(4);
                            break;

                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            return false;
                    }
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, MENUITEM_CLOSE, "Close");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENUITEM_CLOSE:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleEquals(int newOperator) {
        if (hasChanged) {
            switch (operator) {
                case 1:
                    num = num + Double.parseDouble(txtCalc.getText().toString());
                    break;
                case 2:
                    num = num - Double.parseDouble(txtCalc.getText().toString());
                    break;
                case 3:
                    num = num * Double.parseDouble(txtCalc.getText().toString());
                    break;
                case 4:
                    num = num / Double.parseDouble(txtCalc.getText().toString());
                    break;
                case 5:
                    num = Math.pow(num, 2);
                    break;
                case 6:
                    num = Math.pow(num,
                            Double.parseDouble(txtCalc.getText().toString()));
                    break;
                case 7:
                    num = num
                            + Math.sin(Double.parseDouble(txtCalc.getText()
                            .toString()));
                    break;
                case 8:
                    num = num
                            + Math.cos(Double.parseDouble(txtCalc.getText()
                            .toString()));
                    break;
                case 9:
                    num = num
                            + Math.tan(Double.parseDouble(txtCalc.getText()
                            .toString()));
                    break;
                case 10:
                    num = Math
                            .log(Double.parseDouble(txtCalc.getText().toString()));
                    break;
                case 11:
                    double loge = Math.log(Double.parseDouble(txtCalc.getText()
                            .toString()));
                    num = Math.exp(loge);
                    break;
                case 12:
                    num = Math.PI;
                    break;
                case 13:
                    num = Math.E;
                    break;
            }

            String txt = Double.toString(num);
            txtCalc.setText(txt);
            txtCalc.setSelection(txt.length());

            readyToClear = true;
            hasChanged = false;
        }

        operator = newOperator;
    }

    private void handleNumber(int num) {
        if (operator == 0)
            reset();

        String txt = txtCalc.getText().toString();
        if (readyToClear) {
            txt = "";
            readyToClear = false;
        } else if (txt.equals("0"))
            txt = "";

        txt = txt + Integer.toString(num);

        txtCalc.setText(txt);
        txtCalc.setKeyListener(null);
        txtCalc.setSelection(txt.length());

        hasChanged = true;
    }

    private void setValue(String value) {
        if (operator == 0)
            reset();

        if (readyToClear) {
            readyToClear = false;
        }

        txtCalc.setText(value);
        txtCalc.setSelection(value.length());

        hasChanged = true;
    }

    private void handleDecimal() {
        if (operator == 0)
            reset();

        if (readyToClear) {
            txtCalc.setText("0.");
            txtCalc.setSelection(2);
            readyToClear = false;
            hasChanged = true;
        } else {
            String txt = txtCalc.getText().toString();

            if (!txt.contains(".")) {
                txtCalc.append(".");
                hasChanged = true;
            }
        }
    }

    private void handleBackspace() {
        if (!readyToClear) {
            String txt = txtCalc.getText().toString();
            if (txt.length() > 0) {
                txt = txt.substring(0, txt.length() - 1);
                if (txt.equals(""))
                    txt = "0";

                txtCalc.setText(txt);
                txtCalc.setSelection(txt.length());
            }
        }
    }

    private void handlePlusMinus() {
        if (!readyToClear) {
            String txt = txtCalc.getText().toString();
            if (!txt.equals("0")) {
                if (txt.charAt(0) == '-')
                    txt = txt.substring(1, txt.length());
                else
                    txt = "-" + txt;

                txtCalc.setText(txt);
                txtCalc.setSelection(txt.length());
            }
        }
    }

    private void reset() {
        num = 0;
        txtCalc.setText("0");
        txtCalc.setSelection(1);
        operator = 1;
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private void  ProgressTheard()  {

            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            myRef = mFirebaseDatabase.getReference();
            myRef1=myRef.child("users");
            myRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.v(TAG,"hi");
                   // mAuth = FirebaseAuth.getInstance();
                  //  FirebaseUser user = mAuth.getCurrentUser();
                    ///String kol=user.getUid();
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        String fan = ds.getKey();
                        Log.v(TAG, fan);
                        if((fan.equals(usn)))
                        {
                            flag=1;
                            break;
                        }

                        //asyncDialog112.dismiss();
                    }




                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

    private void kon()
    {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mFirebaseDatabase.getReference();
        myRef4=myRef.child("user").child(user.getUid());
        myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pungi = dataSnapshot.getValue(String.class);
                Log.v(TAG,pungi);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private class ProgressTheard2 extends Thread {
        public void run() {}
    }
}


