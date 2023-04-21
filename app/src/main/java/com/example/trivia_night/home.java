package com.example.trivia_night;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



import com.google.android.gms.maps.MapView;

import java.util.concurrent.ConcurrentMap;

public class home extends AppCompatActivity {

    Button btn_viewMap;
    ImageView calender;
    ImageView profile;
    ImageView search;

   // calender.setOnClickListener(new View.OnClickListener()

   // profile.setOnClickListener(new View.OnClickListener()

    //search.setOnClickListener(new View.OnClickListener()

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        calender = findViewById(R.id.calender);
//        Intent intent = new Intent(home.this, Calendar.class);
//        startActivity(intent);
//        profile = findViewById(R.id.profile);
//        Intent intent2 = new Intent(home.this, Profile.class);
//        startActivity(intent2);
//        search = findViewById(R.id.search);
//        Intent intent3 = new Intent(home.this, search_friend.class);
//        startActivity(intent);
//        setContentView(R.layout.activity_home);
//
//        mapView = findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
// First, get references to your ImageViews in your activity or fragment
        calender = findViewById(R.id.calender);
        profile = findViewById(R.id.profile);
        search = findViewById(R.id.search);
        btn_viewMap = findViewById(R.id.btn_viewMap);
        Context context= this;
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String channelId = "Trivia Night Channel";
                String channelName = "Trivia Night Notification Channel";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

                // Create a notification builder

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId);
                builder.setSmallIcon(R.drawable.screenshot1233);
                builder.setContentTitle("Trivia Night");
                builder.setContentText("BattleandBrew Trivia Night starts in few hours! Get Ready!");
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                // Show the notification
                int notificationId = 1; // Unique id for the notification
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
                notificationManager.notify(notificationId, builder.build());
            }
        };

        handler.postDelayed(runnable, 30000);

// Set an OnClickListener for each ImageView
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView1
                Intent intent = new Intent(home.this, Calendar.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView2
                Intent intent = new Intent(home.this, Profile.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView3
                Intent intent = new Intent(home.this, search_friend.class);
                startActivity(intent);
            }
        });
        btn_viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView2
                Intent intent = new Intent(home.this, MapsActivity.class);
                startActivity(intent);
            }
        });



}
}