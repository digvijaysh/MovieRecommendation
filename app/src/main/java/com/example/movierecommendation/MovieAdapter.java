package com.example.movierecommendation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierecommendation.fragments.MovieDetailFragment;
import com.example.movierecommendation.model.Movie;
import com.example.movierecommendation.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movie;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    GoogleSignInAccount account;
    CollectionReference collectionReference;
    FirebaseFirestore firebaseFirestore;


    public MovieAdapter(Context context, List<Movie> movie) {
        this.context = context;
        this.movie = movie;
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        account = GoogleSignIn.getLastSignedInAccount(context);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("users");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(movie.get(position).Title);
        holder.tvDuration.setText(movie.get(position).Runtime);
        int g = movie.get(position).Genre.indexOf(",");
        holder.tvGenre.setText(movie.get(position).Genre.substring(0, g == -1 ? movie.get(position).Genre.length() : g));
        holder.tvReleasedDate.setText(movie.get(position).Year);
        Picasso.get().load(movie.get(position).Poster).into(holder.poster);
        Boolean like = movie.get(position).isLiked;
        holder.likeButton.setLiked(like);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                Fragment fragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("result", movie.get(position).Poster + "#" + movie.get(position).Title + "#" + movie.get(position).Runtime + "#"
                        + movie.get(position).Year + "#" + movie.get(position).Genre + "#" + movie.get(position).Plot);
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.flContainer, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }


    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvGenre;
        TextView tvReleasedDate;
        TextView tvDuration;
        ImageView poster;
        LikeButton likeButton;
        RelativeLayout itemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvReleasedDate = itemView.findViewById(R.id.tvReleaseDate);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            poster = itemView.findViewById(R.id.ivMovieImage);
            likeButton = itemView.findViewById(R.id.thumb_button);

            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    String email = account == null ? FirebaseAuth.getInstance().getCurrentUser().toString() : account.getEmail();
                    collectionReference
                            .whereEqualTo("email", email)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        DocumentReference documentReference = collectionReference.document(documentSnapshot.getId());
                                        documentReference.update("liked", FieldValue.arrayUnion(tvTitle.getText().toString()));
                                    }
                                }
                            });
                }
                @Override
                public void unLiked(LikeButton likeButton) {
                    collectionReference
                            .whereEqualTo("email", account.getEmail())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        DocumentReference documentReference = collectionReference.document(documentSnapshot.getId());
                                        documentReference.update("liked", FieldValue.arrayRemove(tvTitle.getText().toString()));
                                        likeButton.setLiked(false);
                                    }
                                }
                            });
                }
            });
        }
    }


}
