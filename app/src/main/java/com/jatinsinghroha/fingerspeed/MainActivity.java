package com.jatinsinghroha.fingerspeed;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView countDownTV, remainingTapsTV, statusOfGameTV;
    Button tapTapButton;
    CountDownTimer mCountDownTimer;
    int remainingTaps = 10;
    int remainingTime = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownTV = findViewById(R.id.countDownTV);
        remainingTapsTV = findViewById(R.id.remainingTapsTV);
        statusOfGameTV = findViewById(R.id.statusOfGameTV);
        tapTapButton = findViewById(R.id.tapTapBtn);

        tapTapButton.setOnClickListener(v -> {
            if(remainingTaps == 10){
                mCountDownTimer.start();
            }

            remainingTaps--;

            remainingTapsTV.setText(String.valueOf(remainingTaps));

            if(remainingTaps == 0){
                mCountDownTimer.cancel();
                mCountDownTimer = null;
                tapTapButton.setEnabled(false);
                statusOfGameTV.setVisibility(View.VISIBLE);
                statusOfGameTV.setText("Congratulations, You have won.");
            }
        });
    }

    private CountDownTimer getCountDownTimer (int duration){
        return new CountDownTimer(duration*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime --;
                countDownTV.setText(String.valueOf(remainingTime));
                statusOfGameTV.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish() {
                tapTapButton.setEnabled(false);
                statusOfGameTV.setVisibility(View.VISIBLE);
                String text = "You have lost the game, try again";
                statusOfGameTV.setText(text);
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCountDownTimer==null){
            mCountDownTimer = getCountDownTimer(remainingTime);
            if(remainingTime!=60){
                mCountDownTimer.start();
            }
        }
    }
}