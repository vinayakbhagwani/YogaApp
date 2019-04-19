package com.example.himalayabhagwani.yogademoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.himalayabhagwani.yogademoapp.Custom.WorkoutDoneDecorator;
import com.example.himalayabhagwani.yogademoapp.Database.YogaDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Calendar extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    // HashSet<CalendarDay> list = new HashSet<>();

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        yogaDB = new YogaDB(this);

        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendar);

        List<String> workoutDay = yogaDB.getWorkoutDays();
        HashSet<CalendarDay> convertedList = new HashSet<>();

        for(String value:workoutDay)
        {
            convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
        }

        materialCalendarView.addDecorator(new WorkoutDoneDecorator(convertedList));
    }


}
