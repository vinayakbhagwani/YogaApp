package com.example.himalayabhagwani.yogademoapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.himalayabhagwani.yogademoapp.Database.YogaDB;
import com.example.himalayabhagwani.yogademoapp.Utils.Common;

public class ViewExercise extends AppCompatActivity {

    int image_id;
    String name;

    TextView timer, title;
    ImageView detail_image;
    Button btnStartTimer;

    YogaDB yogaDB;

    boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        yogaDB = new YogaDB(this);

        timer = (TextView) findViewById(R.id.timer);
        title = (TextView) findViewById(R.id.titleExe);
        detail_image = (ImageView) findViewById(R.id.imgExe);
        btnStartTimer = (Button) findViewById(R.id.btnStart);

        timer.setText("");

        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRunning) {
                    btnStartTimer.setText("DONE");
                    isRunning = true;

                    int timeLimit = 0;

                    if(yogaDB.getSettingMode() == 0)
                    {
                        timeLimit = Common.TIME_LIMIT_EASY;
                    }
                    else if(yogaDB.getSettingMode() == 1)
                    {
                        timeLimit = Common.TIME_LIMIT_MEDIUM;
                    }
                    else if(yogaDB.getSettingMode() == 2)
                    {
                        timeLimit = Common.TIME_LIMIT_HARD;
                    }

                    new CountDownTimer(timeLimit, 1000){

                        @Override
                        public void onTick(long l) {
                            timer.setText(""+l/1000);
                        }

                        @Override
                        public void onFinish() {
                            //Ads can be placed here
                            if (isRunning) {
                                Toast.makeText(ViewExercise.this, "Finish!!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }.start();
                }
                else {
                    Toast.makeText(ViewExercise.this, "Finish!!!", Toast.LENGTH_SHORT).show();
                    isRunning = false;
                    finish();
                }

            }
        });

        if(getIntent() != null) {

            image_id = getIntent().getIntExtra("image_id",-1);
            name = getIntent().getStringExtra("name");

            detail_image.setImageResource(image_id);
            title.setText(name);


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isRunning = false;
    }
}
