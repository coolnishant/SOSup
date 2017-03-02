package com.example.nish.sosup;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener,SensorEventListener {

    ImageButton ibsos, ibsms, ibcontactdetails;
    final String MY_FILE = "myfile";
    String name1;
    boolean started = false;

    private SensorManager sensorManager;

    int k =0,j=0;
    double longitude;
    double latitude;
    String phone1,myname;
    int n = 0;
    MediaPlayer mPlay;
    TextView tvmyname;
    String loc = "";

    double ax,ay,az;
    long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiallize();
        SharedPreferences prefs = getSharedPreferences(MY_FILE, MODE_PRIVATE);
        boolean set = prefs.getBoolean("setmyname", false);
        if(!set)
           forFirstTime();
        else {
            myname = prefs.getString("myname", "No Name");
            tvmyname.setText("Welcome\n"+myname);
        }
        tvmyname.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

        tvmyname.setOnClickListener(this);

        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        ibsos.setOnClickListener(this);
        ibsms.setOnClickListener(this);

        ibcontactdetails.setOnClickListener(this);
        ibsos.setOnLongClickListener(this);
    }

    private void forFirstTime() {

        myNameInputing();
    }

    private void myNameInputing() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);
        input.requestFocus();
        //input.setText(tvmyname.getText().toString());
// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myname = input.getText().toString();
                tvmyname.setText("Welcome\n"+myname);

                SharedPreferences.Editor editor = getSharedPreferences(MY_FILE, MODE_PRIVATE).edit();
                editor.putBoolean("setmyname",true);
                editor.putString("myname", myname);
                editor.commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void permisssion() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {


                sendSms();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET, Manifest.permission.SEND_SMS}, 2);
            }
        } else {
            sendSms();
        }
    }

    private void initiallize() {
        ibsos = (ImageButton) findViewById(R.id.ibsos);
        ibsms = (ImageButton) findViewById(R.id.ibsms);
        ibcontactdetails = (ImageButton) findViewById(R.id.ibcontactdetail);
        tvmyname = (TextView) findViewById(R.id.tvmyname);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);

        n = 0;

    }


    @Override
    public void onClick(View v) {

        k=0;


        switch (v.getId()) {
            case R.id.ibcontactdetail:
                Intent myIn = new Intent(MainActivity.this, SaveContactAgain2.class);
                startActivity(myIn);
                break;
            case R.id.ibsos:
                sosWarning();
                break;
            /*case R.id.button:
                String s = locationManager.getLastKnownLocation().toString();
                Toast.makeText(this,s,Toast.LENGTH_LONG).show();
                break;
                */
            case R.id.ibsms:
                permisssion();

                break;
            case R.id.tvmyname:
                myNameInputing();
                break;
        }
    }

    private void sosWarning() {
        startPlaying();
        if(n == 1)
            stopPlaying();
    }

    private void startPlaying() {
        AudioManager am =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
        if (n == 0) {
            mPlay = MediaPlayer.create(this, R.raw.siren);
            mPlay.start();
            mPlay.setLooping(true);
            n = 1;
            started = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSms();
        }
        return;
    }

    private void sendSms() {

        phone1 = "+91" + phone1;


        GPSTracker gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            loc = "Lat: " + latitude + "\nLong: " + longitude;
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

            /*Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(myIntent);*/
        }

        LayoutInflater inflater = getLayoutInflater();

        View toastRoot = inflater.inflate(R.layout.toast, null);

        TextView tv = (TextView) toastRoot.findViewById(R.id.texttoast);
        Log.e("a", "Here it is.");
        tv.setText(loc);
        tv.setGravity(Gravity.CENTER);
        //toastRoot.animate().translationX(400).withLayer();
        Toast tos = new Toast(this);
        tos.setDuration(Toast.LENGTH_SHORT);
        tos.setView(toastRoot);
        //https://www.google.co.in/maps/place/25%C2%B014'07.1%22N+86%C2%B058'54.8%22E/@25.2353172,86.9818744,17z/data=!4m5!3m4!1s0x0:0x0!8m2!3d25.2353172!4d86.9818744
        tos.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        tos.show();
        for (int i = 1; i <= 5; i++)
            sendMessage(i);
        String main;
        if(k == 0)
            main = "Add Contact First";
        else
            main = "Message Sent";

        inflater = getLayoutInflater();

        toastRoot = inflater.inflate(R.layout.toast, null);

        tv = (TextView) toastRoot.findViewById(R.id.texttoast);
        Log.e("a", "Here it is.");
        tv.setText(main);
        tv.setTextSize(40);
        tv.setBackgroundColor(Color.RED);
        tv.setTextColor(Color.GREEN);
        tv.setGravity(Gravity.CENTER);
        tos = new Toast(this);
        tos.setDuration(Toast.LENGTH_SHORT);
        tos.setView(toastRoot);
        tos.setGravity(Gravity.FILL_HORIZONTAL | Gravity.CENTER_HORIZONTAL, 0, 550);
        tos.show();

    }

    private void sendMessage(int i) {
        SharedPreferences prefs = getSharedPreferences(MY_FILE, MODE_PRIVATE);
        boolean set = prefs.getBoolean("set" + i, false);
        if (set) {
            k++;
            name1 = prefs.getString("name" + i, "NA");//"No name defined" is the default value.
            phone1 = prefs.getString("number" + i, ""); //0 is the default value.

            String msg = name1 + ",I need Help. "+ myname+"\nLOCATION: " +
                    "http://maps.google.com/maps?q=" + latitude + "," + longitude;

            //sending message
            //Toast.makeText(this,msg+" "+ phone1, Toast.LENGTH_LONG).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone1, null, msg, null, null);
        }
    }

    private void stopPlaying() {
        if (mPlay != null) {
            mPlay.setLooping(false);
            mPlay.stop();
            n = 0;
            started = false;
            mPlay.release();
            mPlay = null;
        }
    }

    @Override
    public void onBackPressed() {

        if(k == -1) {
            k = 0;
            super.onBackPressed();
        }
            else{
            k = 0;
            Toast tos = Toast.makeText(this,"Press Home to exit!!!!",Toast.LENGTH_SHORT);
            tos.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL,0,0);
            View v = tos.getView();
            v.setBackgroundColor(Color.RED);
            tos.setView(v);
            tos.show();
        }
    }

    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.ibsos:
                stopPlaying();
                break;
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];

            double accelationSquareRoot = (ax * ax + ay * ay + az * az) /(SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long actualTime = event.timestamp;
            if (accelationSquareRoot >= 10) {
                if (actualTime - lastUpdate < 800) {
                    return;
                }
                lastUpdate = actualTime;
                if(!started)
                    startPlaying();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
