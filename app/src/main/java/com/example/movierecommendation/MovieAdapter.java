package com.example.movierecommendation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movie;
    BottomNavigationView bottomNavigationView;



    public MovieAdapter(Context context, List<Movie> movie) {
        this.context = context;
        this.movie = movie;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                Fragment fragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("result",movie.get(position).Poster+"#"+movie.get(position).Title+"#"+movie.get(position).Runtime+"#"
                        +movie.get(position).Year+"#"+movie.get(position).Genre+"#"+movie.get(position).Plot);
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
    public class ViewHolder extends RecyclerView.ViewHolder{

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
                    Toast.makeText(context,"Liked",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                }
            });
        }


    }



}
