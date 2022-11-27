package com.example.countapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Switch switch_btn = (Switch)findViewById(R.id.switch_notifications);
        switch_btn.setChecked(sharedPreferences.getBoolean("notifications", true));

        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               editor.putBoolean("notifications", isChecked);
               editor.apply();
                boolean a = sharedPreferences.getBoolean("notifications", true);
                Log.d("Status", String.valueOf(a));
            }


        });

    }
    public void counter_click(View view){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SettingsActivity.this, CounterActivity.class);
                SettingsActivity.this.startActivity(mainIntent);
                SettingsActivity.this.finish();
            }
        }, 0);
    }
}