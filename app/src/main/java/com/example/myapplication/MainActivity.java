package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public EditText login;
    public EditText password;
    public ConstraintLayout error;
    public ConstraintLayout loading;
    public ConstraintLayout n_connection;
    Button button;
    MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        button = findViewById(R.id.button);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        error = findViewById(R.id.error);
        n_connection = findViewById(R.id.n_connection);
        loading = findViewById(R.id.loading);

        SharedPreferences prefs = getSharedPreferences("courses", 0);
        if (!prefs.getString("token", "").equals("")) {
            Intent intent = new Intent(activity.getApplicationContext(), LecturesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.getApplicationContext().startActivity(intent);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadToken loader = new LoadToken();
                loader.activity = activity;
                loader.execute();
            }
        });

    }
}