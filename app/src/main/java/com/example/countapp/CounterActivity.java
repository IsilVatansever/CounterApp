package com.example.countapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
    }
    Integer max_counter_value = 50;
    Integer min_counter_value = 0;


    public void onClick(View v) {
        TextView counter_text = (TextView)findViewById(R.id.tv_counter);
        Integer number = Integer.parseInt((String) counter_text.getText());

        switch (v.getId()) {
            case  R.id.btn_decrease: {
                counter_text.setText(setCounterValue(number, -1));
            break;
            }

            case R.id.btn_increase: {
                counter_text.setText(setCounterValue(number, +1));
            break;
            }
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        TextView counter_text = (TextView)findViewById(R.id.tv_counter);
        Integer number = Integer.parseInt((String) counter_text.getText());

        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                counter_text.setText(setCounterValue(number, -5));
            break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                counter_text.setText(setCounterValue(number, +5));
            break;
        }
        return true;
    }

    public String setCounterValue(Integer current_counter_value, Integer value){
        Integer retVal = current_counter_value;
       if(value > 0 ){
           if((current_counter_value + value) <= max_counter_value){
               retVal = current_counter_value + value;
           }
           else {
               exceed_limit_warning();
           }
       }
       else {
           if((current_counter_value + value) >= min_counter_value){
               retVal = current_counter_value + value;
           }
           else {
               exceed_limit_warning();
           }
       }
       return retVal.toString();
    }

    public void exceed_limit_warning(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

        Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(getBaseContext(), defaultRingtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                }
                
            });
            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}