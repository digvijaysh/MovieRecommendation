package com.example.movierecommendation.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movierecommendation.Config;
import com.example.movierecommendation.MainActivity;
import com.example.movierecommendation.MovieAdapter;
import com.example.movierecommendation.R;
import com.example.movierecommendation.RecommendationClient;
import com.example.movierecommendation.model.FileUtil;
import com.example.movierecommendation.model.Movie;
import com.example.movierecommendation.model.MovieItem;
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RecommendationFragment extends Fragment {

    RecyclerView rvRecommend;
    MovieAdapter movieAdapter;
    List<Movie> movieList;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    GoogleSignInAccount account;
    User user;

    Config config;
    private Handler handler;
    private RecommendationClient client;
    private static final String CONFIG_PATH = "config.json";
    private final List<MovieItem> allMovies = new ArrayList<>();
    private final List<MovieItem> selectedMovies = new ArrayList<>();

    public RecommendationFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User();
        movieList = new ArrayList<>();
        collectionReference = firebaseFirestore.collection("users");
        account = GoogleSignIn.getLastSignedInAccount(getContext());

        try {
            config = FileUtil.loadConfig(getActivity().getAssets(), CONFIG_PATH);
        } catch (IOException ex) {
            Log.e("MainActivity", String.format("Error occurs when loading config %s: %s.", CONFIG_PATH, ex));
        }

        try {
            allMovies.clear();
            allMovies.addAll(FileUtil.loadMovieList(getActivity().getAssets(), config.movieListPath));
        } catch (IOException ex) {
            Log.e("MainActivity", String.format("Error occurs when loading movies %s: %s.", config.movieListPath, ex));
        }

        client = new RecommendationClient(getActivity(), config);
        handler = new Handler();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();
        client.load();
    }

    @Override
    public void onStop() {
        super.onStop();
        client.unload();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRecommend = view.findViewById(R.id.rvRecommend);
        rvRecommend.setLayoutManager(new LinearLayoutManager(getContext()));
        String email = account == null ? FirebaseAuth.getInstance().getCurrentUser().getEmail() : account.getEmail();
        collectionReference
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            User u = documentSnapshot.toObject(User.class);
                            user.id = documentSnapshot.getId();
                            user.email = u.email;
                            user.liked = new ArrayList<>(u.liked);
                            for (MovieItem movieItem : allMovies) {
                                int len = user.liked.size();
                                for (int i = 0; i < len; i++) {
                                    String m = user.liked.get(i);
                                    if (movieItem.title.contains(m)) {
                                        selectedMovies.add(movieItem);
                                        break;
                                    }
                                }
                            }
                            List<RecommendationClient.Result> results = recommend(selectedMovies);
                         //   System.out.println("Results = " + results);
                            database.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot movieSnapshot : snapshot.getChildren()) {
                                        Movie movie = movieSnapshot.getValue(Movie.class);
                                        int len = results.size();
                                        for (int i = 0; i < len; i++) {
                                            MovieItem item = results.get(i).item;
                                            if (movie.Title == null)
                                                continue;
                                            if (item.title.toLowerCase().contains(movie.Title.toLowerCase())) {
                                                if (!movieList.contains(movie))
                                                    movieList.add(movie);
                                            }
                                        }
                                    }
                                    movieAdapter = new MovieAdapter(getActivity(), movieList);
                                    rvRecommend.setAdapter(movieAdapter);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
    }

    private List<RecommendationClient.Result> recommend(final List<MovieItem> movies) {
        Log.d("MainActivity", "Run inference with TFLite model.");
        List<RecommendationClient.Result> recommendations = client.recommend(movies);
        return recommendations;
    }

}