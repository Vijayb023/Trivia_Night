package com.example.trivia_night;

import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.WriteResult;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class Profile extends AppCompatActivity {

    Button testSignout;
    String emailOfUser;

    TextView getUser, getBio, followerCount, followingCount, winCount;

    ImageView profilePicture,search, home, calendar, lobby;
    LinearLayout addBio;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        testSignout = findViewById(R.id.signOutButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        lobby = findViewById(R.id.rocket);
        search = findViewById(R.id.search);
        home = findViewById(R.id.home);
        calendar = findViewById(R.id.calender);


        addBio = findViewById(R.id.addBio);
        getUser = findViewById(R.id.getName);
        getBio = findViewById(R.id.getBio);
        followerCount = findViewById(R.id.followerCount);
        followingCount = findViewById(R.id.FollowingCount);
        winCount = findViewById(R.id.winCount);
        profilePicture = findViewById(R.id.profilePicture);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            emailOfUser = user.getEmail();
        } else {
            // No user is signed in
        }

        //get image from storage if user has an profile pic saved from before
        StorageReference checkPic = storageRef.child(emailOfUser);

        try {
            File localFile = File.createTempFile("tempfile",".png");
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

        //get profile information from firestore
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

                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        //save image uri and call method to save image to cloud storage
        ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Here, no request code
                            Intent data = result.getData();
                            assert data != null;
                            Uri image = data.getData();
                            profilePicture.setImageURI(image);
                            uploadImageToFirebaseStorage(image);
                        }
                    }
                });


        //open gallery and get image uri
        profilePicture.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryResultLauncher.launch(intent);
        });


        addBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Edit Bio");


                // Set up the input
                final EditText input = new EditText(Profile.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DocumentReference bioUpdate = db.collection("users").document(emailOfUser);
                        bioUpdate.update("bio", input.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Profile.this, "Bio Uploaded",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile.this, "Server issue, Bio not updated!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                        getBio.setText(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

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
        lobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), lobby.class);
                startActivity(intent);
                finish();
            }
        });






        //sign out button
        testSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

    private void uploadImageToFirebaseStorage(Uri image){

        StorageReference fileRef = storageRef.child(emailOfUser);
        fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(Profile.this, "Image Uploaded",
                        Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Image Failed to Upload",
                        Toast.LENGTH_SHORT).show();

            }
        });



    }


}