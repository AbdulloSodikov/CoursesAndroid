package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView player;
    TextView shownotes;
    LecturesDatabase db;
    Lecture lecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        player = findViewById(R.id.player);
        shownotes = findViewById(R.id.shownotes);
        db = new LecturesDatabase(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        SharedPreferences prefs = getSharedPreferences("courses", 0);
        lecture = db.getLecture(prefs.getInt("video", 0));

        player.setVideoPath("http://10.155.1.50:9999/episodes/99999/ilmhona/" + lecture.video + ".mp4");
        shownotes.setText(lecture.shownotes);
        player.start();

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.pause();
                    shownotes.setVisibility(View.VISIBLE);
                } else {
                    player.start();
                    shownotes.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}