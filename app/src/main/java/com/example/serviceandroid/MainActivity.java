package com.example.serviceandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MusicStoppedListener {

    ImageView ivPlayStop;
    String audioLink = "https://incompetech.com/music/royalty-free/mp3-royaltyfree/Gymnopedie%20No%201.mp3";
    boolean musicPlaying = false;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPlayStop = (ImageView) findViewById(R.id.ivPlayStop);
        ivPlayStop.setBackgroundResource(R.drawable.play);
        serviceIntent = new Intent(this, MyPlayService.class);

        ApplicationClass.context = (Context) MainActivity.this;

        ivPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!musicPlaying) {
                    playAudio();
                    ivPlayStop.setImageResource(R.drawable.stop);
                    musicPlaying = true;
                } else {
                    stopPlayService();
                    ivPlayStop.setImageResource(R.drawable.play);
                    musicPlaying = false;
                }
            }
        });
    }

    private void playAudio() {
        serviceIntent.putExtra("AudioLink", audioLink);

        try {
            startService(serviceIntent);

        } catch (SecurityException ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void stopPlayService() {
        try {
            stopService(serviceIntent);
        } catch (SecurityException ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMusicStopped() {
        ivPlayStop.setImageResource(R.drawable.play);
        musicPlaying = false;
    }
}