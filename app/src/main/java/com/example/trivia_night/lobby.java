package com.example.trivia_night;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class lobby extends AppCompatActivity {
    ImageView home;
    ImageView calender;
    ImageView search;
    ImageView profile;

    EditText enter;
    Button Button3;
    private static final int MAX_PLAYERS = 6;

    private String lobbyCode;
    private ArrayList<String> players;

    public void PrivateLobby() {
        // Generate a unique lobby code, e.g., using a combination of letters and numbers
        lobbyCode = generateLobbyCode();
        players = new ArrayList<>();
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public boolean isFull() {
        return players.size() >= MAX_PLAYERS;
    }

    public boolean joinLobby(String playerName) {
        if (isFull()) {
            return false; // Lobby is full, cannot join
        }
        players.add(playerName);
        return true; // Joined successfully
    }

    public void startGame() {
        // Start the game logic for the lobby, e.g., initialize game state, notify players, etc.
    }

    private String generateLobbyCode() {

        return "K35XL2";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        calender = findViewById(R.id.calender);
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        profile = findViewById(R.id.profile);
        Button3 = findViewById(R.id.button3);
        enter = findViewById(R.id.LobbyCode);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView1
                Intent intent = new Intent(lobby.this, Calendar.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView2
                Intent intent = new Intent(lobby.this, home.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView3
                Intent intent = new Intent(lobby.this, search_friend.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for imageView3
                Intent intent = new Intent(lobby.this, Profile.class);
                startActivity(intent);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=enter.getText().toString();
                if(input.equals("K35XL2")) {
                    Intent intent = new Intent(lobby.this,lobby_main.class);
                    startActivity(intent);
                }

            }
        });
    }
}
