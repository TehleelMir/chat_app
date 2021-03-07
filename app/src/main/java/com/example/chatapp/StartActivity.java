package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    String email = "";
    String password = "";
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        findViewById(R.id.loginWithFb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithFb();
            }
        });
        findViewById(R.id.loginWithGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });
        findViewById(R.id.newUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void login(){
        if(!set()) return;
        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(StartActivity.this, "You are loged in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StartActivity.this , chatActivity.class));
                }else{
                    Toast.makeText(StartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginWithFb(){
    }

    private void loginWithGoogle(){
    }

    private void newUser(){
        startActivity(new Intent(this , regis.class));
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