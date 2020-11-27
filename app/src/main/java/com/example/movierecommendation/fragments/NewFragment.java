package com.example.movierecommendation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movierecommendation.MovieAdapter;
import com.example.movierecommendation.R;
import com.example.movierecommendation.model.Movie;
import com.example.movierecommendation.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class SortByYear implements Comparator<Movie> {

    @Override
    public int compare(Movie o1, Movie o2) {
        int year1 = Integer.parseInt(o1.Year.substring(0, 4));
        int year2 = Integer.parseInt(o2.Year.substring(0, 4));
        return year2 - year1;
    }
}

public class NewFragment extends Fragment {

    RecyclerView rvNewTab;
    MovieAdapter movieAdapter;
    List<Movie> movieList;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    CollectionReference collectionReference;
    GoogleSignInAccount account;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public NewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionReference = firebaseFirestore.collection("users");
        account = GoogleSignIn.getLastSignedInAccount(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvNewTab = view.findViewById(R.id.rvNewTab);
        rvNewTab.setLayoutManager(new LinearLayoutManager(getContext()));

        movieList = new ArrayList<>();
        String email = account == null ? FirebaseAuth.getInstance().getCurrentUser().toString() : account.getEmail();

        collectionReference
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            User u = documentSnapshot.toObject(User.class);
                            Set<String> moviesLikedByCurrentUser = new HashSet<>(u.liked);
                            database.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot movieSnapshot : snapshot.getChildren()) {
                                        Movie movie = movieSnapshot.getValue(Movie.class);
                                        if (movie.Title != null && movie.Poster != null && !moviesLikedByCurrentUser.contains(movie.Title)) {
                                            movieList.add(movie);
                                        }
                                    }
                                    Collections.sort(movieList, new SortByYear());
                                    movieAdapter = new MovieAdapter(getContext(), movieList);
                                    rvNewTab.setAdapter(movieAdapter);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                });
    }
}