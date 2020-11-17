package com.example.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

//    EditText userName, password;
//    FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        userName = (EditText) findViewById(R.id.editTextTextUserName);
//        password = (EditText) findViewById(R.id.editTextTextPassword);
//
//
//        mAuth = FirebaseAuth.getInstance();
//
//    }
//
//    public void signUp(View view) {
//        Intent intent = new Intent(this,SignUpActivity.class);
//        startActivity(intent);
//    }
//
//    public void login(View view) {
//        String user = userName.getText().toString().trim();
//        String pass = password.getText().toString().trim();
//
//        if (user.isEmpty()) {
//            userName.setError("Username required");
//            userName.requestFocus();
//            return;
//        }
//
//        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
//            userName.setError("Please enter a valid email");
//            userName.requestFocus();
//            return;
//        }
//
//        if (pass.isEmpty()) {
//            password.setError("Password required");
//            password.requestFocus();
//            return;
//        }
//
//        mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        });

}