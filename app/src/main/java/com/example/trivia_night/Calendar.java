package com.example.trivia_night;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.UUID;

public class Calendar extends AppCompatActivity {
    ImageView profile;
    ImageView home;
    ImageView search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        profile = findViewById(R.id.profile);
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);

// Set an OnClickListener for each ImageView
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView1
                Intent intent = new Intent(Calendar.this, Profile.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView2
                Intent intent = new Intent(Calendar.this, home.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView3
                Intent intent = new Intent(Calendar.this, search_friend.class);
                startActivity(intent);
            }
        });
    }
    public void launchShare(View view) {
        String uniqueID = UUID.randomUUID().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://trivianight.app/id="+uniqueID);

        String title = "Share link with Trivia Night!";

        Intent chooserIntent = Intent.createChooser(shareIntent,title);

        if(shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        }



    }
}