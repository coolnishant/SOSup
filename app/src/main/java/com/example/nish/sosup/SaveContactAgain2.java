package com.example.nish.sosup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SaveContactAgain2 extends AppCompatActivity implements View.OnClickListener {

    TextView tvnp1, tvnp2,tvnp3,tvnp4,tvnp5;
    final String MY_FILE = "myfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contact_again2);

        String maintext = "Click on Text To Change/Choose the Contact.";
        LayoutInflater inflater = getLayoutInflater();

        View toastRoot = inflater.inflate(R.layout.toast, null);

        TextView tv = (TextView) toastRoot.findViewById(R.id.texttoast);
        Log.e("a", "Here it is.");
        tv.setText(maintext);
        tv.setTextSize(22);
        tv.setBackgroundColor(Color.GREEN);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        Toast tos = new Toast(this);
        tos.setDuration(4000);
        tos.setView(toastRoot);
        tos.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 300);
        tos.show();

        initialize();
        restoring();
        //permission();
        onClickText();

    }

    private void onClickText() {
        tvnp1.setOnClickListener(this);
        tvnp2.setOnClickListener(this);
        tvnp3.setOnClickListener(this);
        tvnp4.setOnClickListener(this);
        tvnp5.setOnClickListener(this);

    }

    private void storesWith(String name, String number,int n) {
        String key1 = "name"+n,key2 = "number"+n,key3 =  "set"+n;
        SharedPreferences.Editor editor = getSharedPreferences(MY_FILE, MODE_PRIVATE).edit();
        editor.putString(key1, name);
        editor.putBoolean(key3,true);
        editor.putString(key2, number);
        editor.commit();
    }


    private void permission(int ca) {

        if (Build.VERSION.SDK_INT >= 23)
        { if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            Log.e("e","h2");
            accessContact(ca);
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CONTACTS}, 10);
        }
        }
        else {
            accessContact(ca);
        }
    }


    private void accessContact(int ca) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, ca);
        Log.e("a","h1");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        /*if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSms();
        }
        else */if(requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Log.e("w","h3");
            //accessContact();
        }
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                tvnp1.setText(name+"\n"+number);
                storesWith(name,number,1);
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                tvnp2.setText(name+"\n"+number);
                storesWith(name,number,2);
            }
        }
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                tvnp3.setText(name+"\n"+number);
                storesWith(name,number,3);
                storesWith(name,number,3);
            }
        }
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                tvnp4.setText(name+"\n"+number);
                storesWith(name,number,4);
            }
        }
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                tvnp5.setText(name+"\n"+number);
                storesWith(name,number,5);
            }
        }
    }

    private void initialize() {
        tvnp1 = (TextView) findViewById(R.id.tvnp1);
        tvnp2 = (TextView) findViewById(R.id.tvnp2);
        tvnp3 = (TextView) findViewById(R.id.tvnp3);
        tvnp4 = (TextView) findViewById(R.id.tvnp4);
        tvnp5 = (TextView) findViewById(R.id.tvnp5);
    }

    @Override
    public void onClick(View v) {

        SharedPreferences prefs = getSharedPreferences(MY_FILE, MODE_PRIVATE);
        String name , number;
        boolean set;
        switch (v.getId())
        {
            case R.id.tvnp1:
                set = prefs.getBoolean("set1",false);
                if(!set)
                    {
                        permission(1);
                    }
                else {

                    name = prefs.getString("name1","NO Name");
                    number = prefs.getString("number1","NO Name");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage(tvnp1.getText());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteStore(1);
                                    tvnp1.setText("NA");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.tvnp2:
                set = prefs.getBoolean("set2",false);
                if(!set)
                {
                    permission(2);
                }
                else {

                    name = prefs.getString("name2","NO Name");
                    number = prefs.getString("number2","NO Name");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage(tvnp1.getText());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteStore(2);
                                    tvnp2.setText("NA");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.tvnp3:
                set = prefs.getBoolean("set3",false);
                if(!set)
                {
                    permission(3);
                }
                else {

                    name = prefs.getString("name3","NO Name");
                    number = prefs.getString("number3","NO Name");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage(tvnp3.getText());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteStore(3);
                                    tvnp3.setText("NA");
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();

                    alert11.show();
                }
                break;
            case R.id.tvnp4:
                set = prefs.getBoolean("set4",false);
                if(!set)
                {
                    permission(4);
                }
                else {

                    name = prefs.getString("name4","NO Name");
                    number = prefs.getString("number4","NO Name");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage(tvnp4.getText());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteStore(4);
                                    tvnp4.setText("NA");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.tvnp5:
                set = prefs.getBoolean("set5",false);
                if(!set)
                {
                    permission(5);
                }
                else {

                    name = prefs.getString("name5","NO Name");
                    number = prefs.getString("number5","NO Name");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage(tvnp5.getText());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteStore(5);
                                    tvnp5.setText("NA");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;

        }
    }

    private void deleteStore(int n) {
        String key1 = "name"+n,key2 = "number"+n,key3 = "set"+n;
        SharedPreferences.Editor editor = getSharedPreferences(MY_FILE, MODE_PRIVATE).edit();
        editor.putString(key1, "NA");
        editor.putBoolean(key3,false);
        editor.putString(key2,"");
        editor.commit();

    }


    private void restoring() {
        SharedPreferences prefs = getSharedPreferences(MY_FILE, MODE_PRIVATE);
        //String restoredText = prefs.getString("text", null);
        //if (restoredText != null) {
        String name , number;
        boolean set = prefs.getBoolean("set1",false);
        if(set) {
            name = prefs.getString("name1", "NA");//"No name defined" is the default value.
            number = prefs.getString("number1", ""); //0 is the default value.
            tvnp1.setText(name+"\n"+number);
        }
        set = prefs.getBoolean("set2",false);
        if(set) {
            name = prefs.getString("name2", "No name defined");//"No name defined" is the default value.
            number = prefs.getString("number2", "no number"); //0 is the default value.
            tvnp2.setText(name+"\n"+number);
        }
        set = prefs.getBoolean("set3",false);
        if(set) {
            name = prefs.getString("name3", "No name defined");//"No name defined" is the default value.
            number = prefs.getString("number3", "no number"); //0 is the default value.
            tvnp3.setText(name+"\n"+number);
        }
        set = prefs.getBoolean("set4",false);
        if(set) {
            name = prefs.getString("name4", "No name defined");//"No name defined" is the default value.
            number = prefs.getString("number4", "no number"); //0 is the default value.
            tvnp4.setText(name+"\n"+number);

        }
        set = prefs.getBoolean("set5",false);
        if(set) {
            name = prefs.getString("name5", "No name defined");//"No name defined" is the default value.
            number = prefs.getString("number5", "no number"); //0 is the default value.
            tvnp5.setText(name+"\n"+number);
        }
        // }
    }
}
