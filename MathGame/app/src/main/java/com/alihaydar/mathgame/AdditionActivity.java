package com.alihaydar.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.alihaydar.mathgame.databinding.ActivityAdditionBinding;
import com.alihaydar.mathgame.databinding.ActivityMainBinding;

import java.util.Locale;
import java.util.Random;

public class AdditionActivity extends AppCompatActivity {
    private ActivityAdditionBinding binding;
    Random random=new Random();
    int num1,num2;
    int result;
    int userAnswer;
    int userScore=0;
    int userLife=3;
    CountDownTimer timer;
    private static final long START_TIMER_IN_MILES=20000;
    Boolean timer_running;
    long time_left_in_miles=START_TIMER_IN_MILES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAdditionBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        generateRandomNum();
        binding.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer=Integer.parseInt(binding.editTextTextPersonName.getText().toString());
                pauseTimer();

                if(userAnswer==result){
                    userScore+=10;
                    binding.textViewResult.setText(""+userScore);
                    binding.textView8.setText("Congratulations,Your answer is true");
                }else {
                    userLife-=1;
                    binding.textViewLifeScore.setText(""+userLife);
                    binding.textView8.setText("Sorry,Your answer is not true");
                }

            }
        });
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    binding.editTextTextPersonName.setText("");
                    resetTimer();
                    if (userLife<=0){
                        Toast.makeText(getApplicationContext(),"Game Over",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AdditionActivity.this,ResultActivity.class);
                        intent.putExtra("score",userScore);
                        startActivity(intent);
                        finish();

                    }else {
                        generateRandomNum();
                    }


            }
        });
    }

    public void generateRandomNum() {
        num1=random.nextInt(300);
        num2=random.nextInt(300);
        result=num1+num2;
        binding.textView8.setText(num1+" + " +num2);
        startTimer();

    }
    public void startTimer(){
        timer=new CountDownTimer(time_left_in_miles,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_miles=millisUntilFinished;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running=false;
                pauseTimer();
                resetTimer();
                updateText();
                userLife-=1;
                binding.textViewLifeScore.setText(""+userLife);
                binding.textView8.setText("Sorry,Time is up");

            }
        }.start();
        timer_running=true;
    }
    public void updateText(){
        int second= (int) (time_left_in_miles/1000)%60;
        String time_left=String.format(Locale.getDefault(),"%02d",second);
        binding.textViewTimeResult.setText(time_left);



    }
    public void pauseTimer(){
        timer.cancel();
        timer_running=false;

    }
    public void resetTimer(){
        time_left_in_miles=START_TIMER_IN_MILES;
        updateText();

    }
}