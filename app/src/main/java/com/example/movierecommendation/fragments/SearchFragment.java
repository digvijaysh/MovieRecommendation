package com.example.movierecommendation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.movierecommendation.R;
import com.example.movierecommendation.model.Movie;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class SearchFragment extends Fragment {

    EditText editText;
    Button search;
    RecyclerView recyclerView;
    CollectionReference collectionReference;
    GoogleSignInAccount account;
    DatabaseReference databaseReference;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionReference = firebaseFirestore.collection("users");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        account = GoogleSignIn.getLastSignedInAccount(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.ed1);
        recyclerView = view.findViewById(R.id.rvSearch);
        search = view.findViewById(R.id.search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search.setOnClickListener(v -> {
            String result = "";
            String title = editText.getText().toString();
            if (!title.equals("")) {
                String s = title.charAt(0)+"";
                String s1 = title.substring(1,title.length());
                result = s.toUpperCase()+s1;
            }
            firebaseMovieSearch(result);
        });
    }

    private void firebaseMovieSearch(String title) {
        Query query = databaseReference.orderByChild("Title").startAt(title).endAt(title + "\uf8ff");
        FirebaseRecyclerAdapter<Movie, MovieHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Movie, MovieHolder>(
                Movie.class,
                R.layout.item_movie,
                MovieHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(MovieHolder movieHolder, Movie movie, int i) {
                if (movie.Title != null && movie.Poster != null)
                    movieHolder.setDetails(movie.Title, movie.Genre, movie.Year, movie.Runtime, movie.Poster);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {

        View mView;

        public MovieHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(String title, String genre, String year, String duration, String poster1) {

            TextView tvTitle = mView.findViewById(R.id.tvTitle);
            TextView tvGenre = mView.findViewById(R.id.tvGenre);
            TextView tvReleasedDate = mView.findViewById(R.id.tvReleaseDate);
            TextView tvDuration = mView.findViewById(R.id.tvDuration);
            ImageView poster = mView.findViewById(R.id.ivMovieImage);
            LikeButton likeButton = mView.findViewById(R.id.thumb_button);

            int g = genre.indexOf(",");
            tvTitle.setText(title);
            tvGenre.setText(genre.substring(0, g == -1 ? genre.length() : g));
            tvReleasedDate.setText(year);
            tvDuration.setText(duration);
            Picasso.get().load(poster1).into(poster);
        }
    }
}