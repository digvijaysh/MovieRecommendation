package com.example.movierecommendation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movierecommendation.fragments.FavoriteFragment;
import com.example.movierecommendation.fragments.ProfileFragment;
import com.example.movierecommendation.fragments.RecommendationFragment;
import com.example.movierecommendation.fragments.SearchFragment;
import com.example.movierecommendation.fragments.TabFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    GoogleSignInAccount account;
    CollectionReference collectionReference;
    CollectionReference collectionReference1;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        account = GoogleSignIn.getLastSignedInAccount(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("users");
        collectionReference1 = firebaseFirestore.collection("movie");
        addUserToFireStore();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        fragmentManager.beginTransaction().replace(R.id.flContainer,new TabFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        // do something here
                        //Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        fragment = new TabFragment();
                        break;
                    case R.id.action_favorite:
                        // do something here
                        //Toast.makeText(MainActivity.this, "compose", Toast.LENGTH_SHORT).show();
                        fragment = new FavoriteFragment();
                        break;
                    case R.id.action_recommend:
                        // do something here
                        //Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        fragment = new RecommendationFragment();
                        break;
                    case R.id.action_search:
                        // do something here
                        //Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        fragment = new SearchFragment();
                        break;
                    case R.id.action_profile:
                        // do something here
                        //Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    default:
                        return true;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void addUserToFireStore() {
        String email = account == null ? FirebaseAuth.getInstance().getCurrentUser().toString() : account.getEmail();
        collectionReference
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() == 0) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", email);
                            user.put("liked", new ArrayList<>());
                            String id = collectionReference.document().getId();
                            collectionReference.document(id).set(user)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show());
                        } else {
                            Toast.makeText(MainActivity.this, "Record Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void addMovieToFirestore(){

    }
}