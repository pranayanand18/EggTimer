package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    Boolean counter = false;
    CountDownTimer countDownTimer;
    Button button;

    public void resetTimer() {
        textView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        button.setText("GO!");
        counter = false;
    }

    public void startTimer(View view) {
        if (counter) {
            resetTimer();

        }else {

            counter = true;
            seekBar.setEnabled(false);
            button.setText("STOP!");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int i) {
        int min = i / 60;
        int sec = i - (min * 60);
        String seconds = Integer.toString(sec);

        if (sec <= 9) {
            seconds = "0" + seconds;
        }
        textView.setText(Integer.toString(min) + ":" + seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}