package com.example.movierecommendation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.movierecommendation.fragments.FavoriteFragment;
import com.example.movierecommendation.fragments.HomeFragment;
import com.example.movierecommendation.fragments.ProfileFragment;
import com.example.movierecommendation.fragments.RecommendationFragment;
import com.example.movierecommendation.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        // do something here
                        //Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        fragment = new HomeFragment();
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
}