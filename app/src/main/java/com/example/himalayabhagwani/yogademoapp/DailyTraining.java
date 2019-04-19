package com.example.himalayabhagwani.yogademoapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.himalayabhagwani.yogademoapp.Database.YogaDB;
import com.example.himalayabhagwani.yogademoapp.Model.Exercise;
import com.example.himalayabhagwani.yogademoapp.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class DailyTraining extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady,txtCountdown,txtTimer,ex_name;

    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int limit_time=0;
    int flag=0;
    int ex_id=0;

    List<Exercise> exerciseList = new ArrayList<>();

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training);

        initData();

        yogaDB = new YogaDB(this);

        btnStart = (Button) findViewById(R.id.btnStart);
        ex_image = (ImageView) findViewById(R.id.imgExe);
        ex_name = (TextView) findViewById(R.id.titleExe);
        progressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        layoutGetReady = (LinearLayout) findViewById(R.id.layout_get_ready);
        txtGetReady = (TextView) findViewById(R.id.txtGetReady);
        txtCountdown = (TextView) findViewById(R.id.txtCountDown);
        txtTimer = (TextView) findViewById(R.id.timer);

        progressBar.setMax(exerciseList.size());

        if(yogaDB.getSettingMode() == 0)
        {
            txtTimer.setText(""+Common.TIME_LIMIT_EASY/1000);
        }
        else if(yogaDB.getSettingMode() == 1)
        {
            txtTimer.setText(""+Common.TIME_LIMIT_MEDIUM/1000);
        }
        else if(yogaDB.getSettingMode() == 2)
        {
            txtTimer.setText(""+Common.TIME_LIMIT_HARD/1000);
        }

        setExerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnStart.getText().toString().toLowerCase().equals("start")) {
                    showGetReady();

                }
                else if(btnStart.getText().toString().toLowerCase().equals("done")) {

                    if(yogaDB.getSettingMode() == 0)
                    {
                        easyModeExercisesCountDown.cancel();
                    }
                    else if(yogaDB.getSettingMode() == 1)
                    {
                        mediumModeExercisesCountDown.cancel();
                    }
                    else if(yogaDB.getSettingMode() == 2)
                    {
                        hardModeExercisesCountDown.cancel();
                    }

                    restTimeCountDown.cancel();

                    if(ex_id < exerciseList.size())
                    {
                        showRestTime();
                    }

                }
                else if(btnStart.getText().toString().toLowerCase().equals("skip")) {

                    if(ex_id < exerciseList.size()-1)
                    {
                        flag = 2;
                        restTimeCountDown.cancel();
                        ex_id++;
                        setExerciseInformation(ex_id);
                    }
                    else {
                        flag = 2;
                        restTimeCountDown.cancel();
                        progressBar.setProgress(exerciseList.size());
                        showFinished();
                    }

                }

            }
        });

    }

    private void showRestTime() {

        progressBar.setProgress(ex_id);
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);
        txtGetReady.setVisibility(View.VISIBLE);
        txtCountdown.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);

        txtGetReady.setText("REST TIME");

        btnStart.setText("Skip");

        flag = 1;
        restTimeCountDown.start();



    }

    private void showGetReady() {

        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);
        txtGetReady.setVisibility(View.VISIBLE);
        txtCountdown.setVisibility(View.VISIBLE);

        txtGetReady.setText("Get Ready");

        new CountDownTimer(6000, 1000) {

            @Override
            public void onTick(long l) {
                txtCountdown.setText(""+l/1000);
            }

            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();

    }

    private void showExercises() {
        if (ex_id < exerciseList.size()) {

            ex_image.setImageResource(exerciseList.get(ex_id).getImage_id());
            ex_name.setText(exerciseList.get(ex_id).getName());

            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            txtTimer.setVisibility(View.VISIBLE);

            btnStart.setText("Done");

            layoutGetReady.setVisibility(View.INVISIBLE);
            txtGetReady.setVisibility(View.INVISIBLE);
            txtCountdown.setVisibility(View.INVISIBLE);

            if(yogaDB.getSettingMode() == 0)
            {
                easyModeExercisesCountDown.start();
            }
            else if(yogaDB.getSettingMode() == 1)
            {
                mediumModeExercisesCountDown.start();
            }
            else if(yogaDB.getSettingMode() == 2)
            {
                hardModeExercisesCountDown.start();
            }

        }
        else {
            showFinished();
        }

    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);
        txtGetReady.setVisibility(View.VISIBLE);
        txtCountdown.setVisibility(View.VISIBLE);


        txtCountdown.setTextSize(20);
        txtGetReady.setText("FINISHED");
        txtCountdown.setText("Congratulations ! \n You're done with today's exercises \n Go in the 'Calendar' option to view your progress");


        yogaDB.setWorkoutDays(""+ Calendar.getInstance().getTimeInMillis());

    }

    CountDownTimer easyModeExercisesCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText("" + l/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id < exerciseList.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setVisibility(View.VISIBLE);
                txtTimer.setText(""+(Common.TIME_LIMIT_EASY/1000));
                setExerciseInformation(ex_id);
            }
            else {
                progressBar.setProgress(exerciseList.size());
                showFinished();
            }
        }
    };

    CountDownTimer mediumModeExercisesCountDown = new CountDownTimer(20000, 1000) {
        @Override
        public void onTick(long l) {

            txtTimer.setText("" + l/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id < exerciseList.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setVisibility(View.VISIBLE);
                txtTimer.setText("" + (Common.TIME_LIMIT_MEDIUM/1000));
                setExerciseInformation(ex_id);

            }
            else {
                progressBar.setProgress(exerciseList.size());
                showFinished();
            }
        }
    };

    CountDownTimer hardModeExercisesCountDown = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText("" + l/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id < exerciseList.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setVisibility(View.VISIBLE);
                txtTimer.setText("" + (Common.TIME_LIMIT_HARD/1000));
                setExerciseInformation(ex_id);

            }
            else {
                progressBar.setProgress(exerciseList.size());
                showFinished();
            }
        }
    };

    CountDownTimer restTimeCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {
            txtCountdown.setText(""+l/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id < exerciseList.size()-1)
            {
                if(flag == 1)
                {
                    ex_id++;
                    setExerciseInformation(ex_id);
                }

            }
            else {
                progressBar.setProgress(exerciseList.size());
                showFinished();
            }
        }
    };


    private void setExerciseInformation(int id) {

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        ex_image.setImageResource(exerciseList.get(id).getImage_id());
        ex_name.setText(exerciseList.get(id).getName());
        btnStart.setText("Start");
        progressBar.setProgress(ex_id);

        if(yogaDB.getSettingMode() == 0)
        {
            txtTimer.setText(""+Common.TIME_LIMIT_EASY/1000);
        }
        else if(yogaDB.getSettingMode() == 1)
        {
            txtTimer.setText(""+Common.TIME_LIMIT_MEDIUM/1000);
        }
        else if(yogaDB.getSettingMode() == 2)
        {
            txtTimer.setText(""+Common.TIME_LIMIT_HARD/1000);
        }

        layoutGetReady.setVisibility(View.INVISIBLE);
        txtGetReady.setVisibility(View.INVISIBLE);
        txtCountdown.setVisibility(View.INVISIBLE);

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
