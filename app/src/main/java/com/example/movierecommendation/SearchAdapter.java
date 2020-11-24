package com.example.movierecommendation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {

    Context context;
    List<Movie> movie;
    List<Movie> movieFull;


    public SearchAdapter(Context context, List<Movie> movie) {
        this.context = context;
        this.movie = movie;
        movieFull = new ArrayList<>(movie);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvGenre;
        TextView tvReleasedDate;
        TextView tvDuration;
        ImageView poster;
        LikeButton likeButton;

        public SearchViewHolder(@NonNull View itemView) {
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
                    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                }
            });
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.tvTitle.setText(movie.get(position).Title);
        holder.tvDuration.setText(movie.get(position).Runtime);
        int g = movie.get(position).Genre.indexOf(",");
        holder.tvGenre.setText(movie.get(position).Genre.substring(0, g == -1 ? movie.get(position).Genre.length() : g));
        holder.tvReleasedDate.setText(movie.get(position).Year);
        Picasso.get().load(movie.get(position).Poster).into(holder.poster);
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

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(movie);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Movie movie : movie) {
                    if (movie.Title.toLowerCase().contains(filterPattern)) {
                        filteredList.add(movie);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
             movie.clear();
             movieFull.addAll((List)results.values);
             notifyDataSetChanged();
        }
    };

}
