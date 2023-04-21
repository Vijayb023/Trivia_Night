package com.example.trivia_night;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class search_friend extends AppCompatActivity {
    ImageView profile;
    ImageView home;
    ImageView calender;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    String emailOfUser;

    ImageView addFriendsPage;
    private static List<String> document22 = new ArrayList<>(); //contains first and last name for list view
    private static List<String> emails = new ArrayList<>(); //contains email for profile details
    ListView listView;

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


        addFriendsPage = findViewById(R.id.addFriendsPage);

        addFriendsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), add_friends.class);
                startActivity(intent);
                finish();
            }
        });

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            emailOfUser = user.getEmail();
        } else {
            // No user is signed in
            Toast.makeText(search_friend.this, "Server Error!",
                    Toast.LENGTH_SHORT).show();
        }



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document(emailOfUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    document22.clear();
                    if (document.exists()) {
                        //get follower email from logged in user following field
                        List<String> following = (List<String>) document.get("Following");



                        for(int i=0;i<following.size();i++) {
                            DocumentReference docRef = db.collection("users").document(following.get(i));
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        String firstname = (String) document.get("First Name");
                                        String lastname = (String) document.get("Last Name");
                                        String email = (String) document.get("email");

                                        String fullName = firstname + " " + lastname;
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                        document22.add(fullName);
                                        emails.add(email);
                                        Log.d(TAG, "Data: " + document22.toString());
                                        listView = findViewById(R.id.listView);

                                        ArrayAdapter arrayAdapter =new ArrayAdapter((Context)search_friend.this, android.R.layout.simple_list_item_1, document22);
                                        listView.setAdapter(arrayAdapter);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                String name = listView.getItemAtPosition(i).toString();
                                                String email = emails.get(i);

                                                Intent intent = new Intent(getBaseContext(), Following_Profile.class);
                                                intent.putExtra("following_user_name", name);
                                                intent.putExtra("following_user_email", email);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        Log.d(TAG, "ALLLL: " + document22.toString());




    }
}