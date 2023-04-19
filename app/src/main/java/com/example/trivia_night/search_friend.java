package com.example.trivia_night;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class search_friend extends AppCompatActivity {
    ImageView profile;
    ImageView home;
    ImageView calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        calender = findViewById(R.id.calender);
        profile = findViewById(R.id.profile);
        home = findViewById(R.id.search);

// Set an OnClickListener for each ImageView
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView1
                Intent intent = new Intent(search_friend.this, Calendar.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView2
                Intent intent = new Intent(search_friend.this, Profile.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView3
                Intent intent = new Intent(search_friend.this, home.class);
                startActivity(intent);
            }
        });
    }
}