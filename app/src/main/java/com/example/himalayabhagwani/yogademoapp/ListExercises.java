package com.example.himalayabhagwani.yogademoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.himalayabhagwani.yogademoapp.Adapter.RecyclerViewAdapter;
import com.example.himalayabhagwani.yogademoapp.Model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ListExercises extends AppCompatActivity {

    List<Exercise> exerciseList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.lv);

        adapter = new RecyclerViewAdapter(exerciseList,getBaseContext());

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void initData() {

        exerciseList.add(new Exercise(R.drawable.easy_pose,"Easy Pose"));
        exerciseList.add(new Exercise(R.drawable.cobra_pose,"Cobra Pose"));
        exerciseList.add(new Exercise(R.drawable.downward_facing_dog,"Downward Facing Dog"));
        exerciseList.add(new Exercise(R.drawable.boat_pose,"Boat Pose"));
        exerciseList.add(new Exercise(R.drawable.half_pigeon,"Half Pigeon"));
        exerciseList.add(new Exercise(R.drawable.low_lunge,"Low Lunge"));
        exerciseList.add(new Exercise(R.drawable.upward_bow,"Upward Pose"));
        exerciseList.add(new Exercise(R.drawable.crescent_lunge,"Crescent Lunge"));
        exerciseList.add(new Exercise(R.drawable.warrior_pose,"Warrior Pose"));
        exerciseList.add(new Exercise(R.drawable.bow_pose,"Bow Pose"));
        exerciseList.add(new Exercise(R.drawable.warrior_pose_2,"Warrior Pose 2"));

    }
}
