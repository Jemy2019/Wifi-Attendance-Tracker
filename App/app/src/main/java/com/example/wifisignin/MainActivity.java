package com.example.wifisignin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button dis,con;
    private EditText ssid,pass;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText ssid = findViewById(R.id.ssid);
        final EditText pass = findViewById(R.id.pass);
        sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        String ssidStr = sharedPreferences.getString("ssid","");
        String passStr = sharedPreferences.getString("pass","");
        WorkManager.getInstance().enqueue(
                new PeriodicWorkRequest.Builder(
                        Signing.class,
                Constants.THIRTY_MINUTES_IN_SECONDS,
                TimeUnit.SECONDS
                ).build());

        dis = findViewById(R.id.disconnect);
        con = findViewById(R.id.connect);

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiHelper.disConnect(MainActivity.this);
            }
        });

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ssid.getText().toString().trim().length()>0 && pass.getText().toString().trim().length()>0) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ssid", ssid.getText().toString());
                    editor.putString("pass", pass.getText().toString());
                    editor.apply();
                }
                WifiHelper.signIn(MainActivity.this);
            }
        });
    }


}
