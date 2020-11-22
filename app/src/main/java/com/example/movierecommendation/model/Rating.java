
package com.example.movierecommendation.model;

public class Rating {

    public String Source;
    public String Value;

    public Rating(){

    }

    @Override
    public String toString() {
        return "Rating{" +
                "Source='" + Source + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
