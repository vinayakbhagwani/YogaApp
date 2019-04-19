package com.example.himalayabhagwani.yogademoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.himalayabhagwani.yogademoapp.Database.YogaDB;

import java.util.Calendar;

public class SettingPage extends AppCompatActivity {

    Button btnSave;
    RadioGroup rdGroup;
    RadioButton rdEasy, rdMedium, rdHard;
    ToggleButton switchAlarm;
    TimePicker timePicker;
    YogaDB yogaDB;

    int hr,min;

    Intent intent;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);
        rdEasy = (RadioButton) findViewById(R.id.easy);
        rdMedium = (RadioButton) findViewById(R.id.medium);
        rdHard = (RadioButton) findViewById(R.id.hard);

        switchAlarm = (ToggleButton) findViewById(R.id.tb);

        timePicker = (TimePicker) findViewById(R.id.tp);

        yogaDB = new YogaDB(this);


        int mode = yogaDB.getSettingMode();
        setRadioButton(mode);

        switchAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchAlarm.isChecked()) {

                    switchAlarm.setChecked(true);
                    switchAlarm.setTextColor(getResources().getColor(R.color.colorAccent));
                    switchAlarm.setBackgroundColor(getResources().getColor(R.color.green));
                } else {

                    switchAlarm.setChecked(false);
                    switchAlarm.setTextColor(getResources().getColor(R.color.colorAccent));
                    switchAlarm.setBackgroundColor(getResources().getColor(R.color.red));
                }

            }
        });

        if (yogaDB.getAlarmMode() == 1) {

            switchAlarm.setChecked(true);
            switchAlarm.setTextColor(getResources().getColor(R.color.colorAccent));
            switchAlarm.setBackgroundColor(getResources().getColor(R.color.green));

        } else if (yogaDB.getAlarmMode() == 0) {

            switchAlarm.setChecked(false);
            switchAlarm.setTextColor(getResources().getColor(R.color.colorAccent));
            switchAlarm.setBackgroundColor(getResources().getColor(R.color.red));

        }


        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWorkoutMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(SettingPage.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void saveAlarm(boolean checked) {

        if (checked) {

            yogaDB.setAlarmMode(1);

            Calendar calendar = Calendar.getInstance();

            if (Build.VERSION.SDK_INT >= 23 )
            {
                hr = timePicker.getHour();
                min = timePicker.getMinute();
            }
            else {
                hr = timePicker.getCurrentHour();
                min = timePicker.getCurrentMinute();
            }

            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);

            intent = new Intent(SettingPage.this, AlarmNotificationReceiver.class);

            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

            // manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        } else {

            yogaDB.setAlarmMode(0);

            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

            intent = new Intent(SettingPage.this, AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            manager.cancel(pendingIntent);

        }
    }

    private void saveWorkoutMode() {

        int selectedID = rdGroup.getCheckedRadioButtonId();
        if (selectedID == rdEasy.getId()) {
            yogaDB.setSettingMode(0);
        } else if (selectedID == rdMedium.getId()) {
            yogaDB.setSettingMode(1);
        } else if (selectedID == rdHard.getId()) {
            yogaDB.setSettingMode(2);
        }

    }

    private void setRadioButton(int mode) {
        if (mode == 0) {
            rdGroup.check(R.id.easy);
        } else if (mode == 1) {
            rdGroup.check(R.id.medium);
        } else if (mode == 2) {
            rdGroup.check(R.id.hard);
        }

    }

}
