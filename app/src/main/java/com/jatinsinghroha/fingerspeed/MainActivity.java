package com.jatinsinghroha.fingerspeed;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.jatinsinghroha.fingerspeed.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bind;

    CountDownTimer mCountDownTimer;
    int remainingTaps = 350;
    int remainingTime = 60;
    private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        setContentView(view);

        bind.playPauseButton.setOnClickListener(v -> {
            managePlayPause();
        });

        bind.tapTapBtn.setOnClickListener(v -> {
            if(remainingTaps == 350){
                bind.playPauseButton.setChecked(true);
                mCountDownTimer.start();
            }

            remainingTaps--;

            bind.remainingTapsTV.setText(String.valueOf(remainingTaps));

            if(remainingTaps == 0){
                mCountDownTimer.cancel();
                mCountDownTimer = null;
                bind.tapTapBtn.setEnabled(false);
                bind.statusOfGameTV.setVisibility(View.VISIBLE);
                bind.statusOfGameTV.setText("Congratulations, You have won.");
            }
        });
    }

    private CountDownTimer getCountDownTimer (int duration){
        return new CountDownTimer(duration*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime --;
                bind.countDownTV.setText(String.valueOf(remainingTime));
            }

            @Override
            public void onFinish() {
                cancel();
                bind.tapTapBtn.setEnabled(false);
                bind.statusOfGameTV.setVisibility(View.VISIBLE);
                String text = "You have lost the game, try again";
                bind.statusOfGameTV.setText(text);

                bind.playPauseButton.setChecked(false);
                bind.playPauseButton.setTextOff("PLAY AGAIN");

                remainingTime = 60;
                remainingTaps = 350;
                gameOver = true;
                managePlayPause();
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

    private void onResult(String title, String message, int image){
        /**
         * implement dialog box for win/loss with 2/3 action buttons.
          */
    }

    private void managePlayPause(){
        if(mCountDownTimer!=null && remainingTime != 60){
            bind.playPauseButton.setTextOff("Resume the game");
            bind.playPauseButton.setChecked(false);
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        else if(mCountDownTimer == null){
            bind.tapTapBtn.setEnabled(true);
            gameOver = false;
            mCountDownTimer = getCountDownTimer(remainingTime);
            mCountDownTimer.start();
            bind.playPauseButton.setChecked(true);
        }
        else if(gameOver){
            bind.playPauseButton.setTextOff("RETRY - PLAY AGAIN");
            bind.playPauseButton.setChecked(false);
            mCountDownTimer = null;
        }
        else{
            bind.playPauseButton.setChecked(true);
            mCountDownTimer.start();
            Toast.makeText(MainActivity.this, "Game has started.", Toast.LENGTH_SHORT);
        }
    }
}