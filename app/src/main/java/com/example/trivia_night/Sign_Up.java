package com.example.trivia_night;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Sign_Up extends AppCompatActivity {

    TextInputEditText editFirst, editLast, editEmail, editPass;
    ImageView buttonSignUp;
    FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        editFirst =findViewById(R.id.editText);
        editLast =findViewById(R.id.editText2);
        editEmail =findViewById(R.id.textInputEditText);
        editPass =findViewById(R.id.textInputEditText2);

        buttonSignUp = findViewById(R.id.imageView4);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, firstName, lastName;


                firstName = String.valueOf(editFirst.getText());
                lastName = String.valueOf(editLast.getText());
                email = String.valueOf(editEmail.getText());
                password = String.valueOf(editPass.getText());

                if(TextUtils.isEmpty(firstName)){
                    Toast.makeText(Sign_Up.this,"Enter First Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lastName)){
                    Toast.makeText(Sign_Up.this,"Enter Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Sign_Up.this,"Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Sign_Up.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> user = new HashMap<>();
                user.put("First Name", firstName);
                user.put("Last Name", lastName);
                user.put("email", email);
                user.put("followCount", 0);
                user.put("followingCount", 0);
                user.put("wins", 0);
                user.put("bio", "no bio");
                user.put("Following", Arrays.asList());

                WriteBatch writeBatch = db.batch();
                writeBatch.set(reference.document(email), user);
                writeBatch.commit();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Sign_Up.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Sign_Up.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }
}