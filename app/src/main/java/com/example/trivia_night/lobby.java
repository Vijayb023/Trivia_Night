package com.example.trivia_night;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class lobby extends AppCompatActivity {

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
    }
}