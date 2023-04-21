package com.example.trivia_night;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.v1.WriteResult;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Following_Profile extends AppCompatActivity {

    TextView getName;


    //for profile pic
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    ImageView profilePicture, search, home, calendar, profile;

    TextView getUser, getBio, followerCount, followingCount, winCount;

    String loggedInEmail;
    String emailOfUser;

    Button followButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            loggedInEmail = user.getEmail();
            Log.d(TAG, "DocumentSnapshot data: " + loggedInEmail);
        } else {
            // No user is signed in
            Toast.makeText(Following_Profile.this, "Server Error!",
                    Toast.LENGTH_SHORT).show();
        }

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

        String sessionId = getIntent().getStringExtra("following_user_name");
        getName = findViewById(R.id.getName);
        getName.setText(sessionId);
        emailOfUser = getIntent().getStringExtra("following_user_email");
        Log.d(TAG, "Email: " + emailOfUser);
        Log.d(TAG, "sess: " + sessionId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        profilePicture = findViewById(R.id.profilePicture);
        getUser = findViewById(R.id.getName);
        getBio = findViewById(R.id.getBio);
        followerCount = findViewById(R.id.followerCount);
        followingCount = findViewById(R.id.FollowingCount);
        winCount = findViewById(R.id.winCount);


        //get image from storage if user has an profile pic saved from before
        StorageReference checkPic = storageRef.child(emailOfUser);

        try {
            File localFile = File.createTempFile("follower",".png");
            checkPic.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Picasso.get().load(localFile).into(profilePicture);
                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }
        //end profile picture get from database


        //get profile details
        DocumentReference docRef = db.collection("users").document(emailOfUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String firstname = (String) document.get("First Name");
                        String lastname = (String) document.get("Last Name");
                        long followCount1 = (Long) document.get("followCount");
                        long followingCount1 = (Long) document.get("followingCount");
                        String bio = (String) document.get("bio");
                        long wins1 = (Long) document.get("wins");

                        String wins = Long.toString(wins1);
                        String followCount = Long.toString(followCount1);
                        String followingCount2 = Long.toString(followingCount1);


                        getUser.setText(firstname+" "+lastname);
                        getBio.setText(bio);
                        followerCount.setText("Followers: "+followCount);
                        followingCount.setText("Following: "+followingCount2);
                        winCount.setText("Wins: "+wins);
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        DocumentReference documentReference = db.collection("users").document(loggedInEmail);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {

                                        List<String> check = (List<String>) document2.get("Following");
                                        Log.d(TAG, "DocumentSnapshot data: " + check.toString());

                                        if(check.contains(emailOfUser)){

                                            followButton = findViewById(R.id.followButton);
                                            followButton.setText("UNFOLLOW");
                                            followButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    docRef.update("followCount",FieldValue.increment(-1));
                                                    documentReference.update("followingCount",FieldValue.increment(-1));
                                                    documentReference.update("Following",FieldValue.arrayRemove(emailOfUser));
                                                    Toast.makeText(Following_Profile.this, "Unfollowed",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }else{

                                            followButton = findViewById(R.id.followButton);
                                            followButton.setText("FOLLOW");
                                            followButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Map<String, Object> user = new HashMap<>();
                                                    docRef.update("followCount",FieldValue.increment(1));
                                                    documentReference.update("followingCount",FieldValue.increment(1));

                                                    documentReference.update("Following",FieldValue.arrayUnion(emailOfUser));
                                                    Toast.makeText(Following_Profile.this, "Following",
                                                            Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                        }


                                    }

                                }




                            }
                        });




                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        //TODO: unfollow logic





    }
}