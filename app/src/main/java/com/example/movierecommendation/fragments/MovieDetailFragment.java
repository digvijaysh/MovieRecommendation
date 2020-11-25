package com.example.movierecommendation.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movierecommendation.R;
import com.example.movierecommendation.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Arrays;


public class MovieDetailFragment extends Fragment {

    ImageView imageView;
    TextView title, duration, plot;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        imageView = view.findViewById(R.id.imageView);
        title = view.findViewById(R.id.textView);
        duration = view.findViewById(R.id.textView2);
        plot = view.findViewById(R.id.tv);
        Bundle bundle = this.getArguments();
        String[] data = bundle.getString("result").split("#");
        int g = data[4].indexOf(",");
      //  System.out.println(Arrays.toString(data));
        Picasso.get().load(data[0]).into(imageView);
        title.setText(data[1]);
        duration.setText(data[4].substring(0,g==-1?data[4].length():g)+" | "+data[3]+" | "+data[2]);
        plot.setText(data[5]);
        return view;
    }
}