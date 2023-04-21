package com.example.trivia_night;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class add_friends extends AppCompatActivity {

    SearchView searchView;

    ListView listView;



    private static List<String> document22 = new ArrayList<>(); //contains first and last name for list view
    private static List<String> emails = new ArrayList<>(); //contains email for profile details

    private static String[] names = new String[1];
    private static String[] emails2 = new String[1];

    private static String[] noResults = {"No Results"};

    ImageView profile, home, calendar, search;

    ImageView FriendsPage;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        search = findViewById(R.id.search);
        home = findViewById(R.id.home);
        calendar = findViewById(R.id.calender);
        profile = findViewById(R.id.profile);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_friend.class);
                startActivity(intent);
                finish();

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Calendar.class);
                startActivity(intent);
                finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
                finish();
            }
        });

        FriendsPage = findViewById(R.id.imageView14);

        FriendsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_friend.class);
                startActivity(intent);
                finish();
                recreate();
            }
        });

        searchView = (SearchView) findViewById(R.id.searchView);

        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String queryEmail = searchView.getQuery().toString();


                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("users").document(queryEmail);
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

                            names[0]=fullName;
                            emails2[0]=email;
                            //Log.d(TAG, "Data: " + document22.toString());
                            listView = findViewById(R.id.listView);

                            ArrayAdapter arrayAdapter =new ArrayAdapter((Context)add_friends.this, android.R.layout.simple_list_item_1, names);
                            listView.setAdapter(arrayAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    String name = listView.getItemAtPosition(i).toString();
                                    String email = emails2[0];

                                    Intent intent = new Intent(getBaseContext(), Following_Profile.class);
                                    intent.putExtra("following_user_name", name);
                                    intent.putExtra("following_user_email", email);
                                    startActivity(intent);
                                }
                            });
                        }else{

                            ArrayAdapter arrayAdapter =new ArrayAdapter((Context)add_friends.this, android.R.layout.simple_list_item_1, names);
                            listView.setAdapter(arrayAdapter);



                        }
                    }
                });

            }
        });



    }

    public void search(View v){
        String queryEmail = searchView.getQuery().toString();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document(queryEmail);
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

                    names[0]=fullName;
                    emails2[0]=email;
                    //Log.d(TAG, "Data: " + document22.toString());
                    listView = findViewById(R.id.listView);

                    ArrayAdapter arrayAdapter =new ArrayAdapter((Context)add_friends.this, android.R.layout.simple_list_item_1, names);
                    listView.setAdapter(arrayAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            String name = listView.getItemAtPosition(i).toString();
                            String email = emails2[0];

                            Intent intent = new Intent(getBaseContext(), Following_Profile.class);
                            intent.putExtra("following_user_name", name);
                            intent.putExtra("following_user_email", email);
                            startActivity(intent);
                        }
                    });
                }else{

                    ArrayAdapter arrayAdapter =new ArrayAdapter((Context)add_friends.this, android.R.layout.simple_list_item_1, names);
                    listView.setAdapter(arrayAdapter);



                }
            }
        });
    }
}