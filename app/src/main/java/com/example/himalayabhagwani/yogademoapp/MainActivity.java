package com.example.himalayabhagwani.yogademoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openList(View view) {
        Intent i = new Intent(MainActivity.this, ListExercises.class);
        startActivity(i);
    }

    public void openSettings(View view) {
        Intent i = new Intent(MainActivity.this, SettingPage.class);
        startActivity(i);
    }

    public void startTraining(View view) {
        Intent i = new Intent(MainActivity.this, DailyTraining.class);
        startActivity(i);
    }

    public void openCalendar(View view) {
        Intent i = new Intent(MainActivity.this, Calendar.class);
        startActivity(i);
    }

    public void playMusic(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm")
                .setIcon(R.drawable.play_icon)
                .setMessage("Want to play Meditational background music while doing Yoga ?")
                .setCancelable(false)
                .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent svc=new Intent(MainActivity.this, BackgroundSoundService.class);
                        startService(svc);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
