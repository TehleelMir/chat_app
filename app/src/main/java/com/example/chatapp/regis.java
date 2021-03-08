package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class regis extends AppCompatActivity {
    String email = "" , password = "";
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        findViewById(R.id.reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg();
            }
        });
        findViewById(R.id.singnUpWithFb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singnUpWithFb();
            }
        });
        findViewById(R.id.signUpWithGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpWithGoogle();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        reference = firebaseDatabase.getReference("users").
                child(firebaseAuth.getCurrentUser().getUid());
    }

    private void reg(){
        if(!set()) return;
        firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(regis.this, "new user have been reg", Toast.LENGTH_SHORT).show();
                    uploadAllTheDataToCloud();
                }else{
                    Toast.makeText(regis.this , "error" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadAllTheDataToCloud(){
        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    uploadUsersData temp = new uploadUsersData(email);
                    reference.setValue(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(regis.this, "users email is now his/her id also", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(regis.this , chatActivity.class));
                            }
                        }
                    });
                }
            }
        });

    }

    private void signUpWithGoogle(){

    }

    private void singnUpWithFb(){

    }

    private boolean set(){
        EditText et = findViewById(R.id.email);
        email = et.getText().toString();
        et = findViewById(R.id.password);
        password = et.getText().toString();

        if(email.equals("") || password.equals("") || password.length() < 6){
            Toast.makeText(this, "wrong input", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}