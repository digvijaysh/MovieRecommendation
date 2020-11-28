package com.example.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {

    EditText userName, password;
    Button signUp;
    private FirebaseAuth mAuth;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
    }


    public void registerUser(View view) {
        String user = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty()) {
            userName.setError("Username required");
            userName.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            userName.setError("Please enter a valid email");
            userName.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,GenreActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(getApplicationContext(), "Already Registered or Username Already Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}