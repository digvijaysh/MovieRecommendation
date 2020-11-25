
package com.example.movierecommendation.model;


import java.util.List;

public class Movie {
    public String Metascore;
    public String BoxOffice;
    public String Website;
    public List<Rating> Ratings = null;
    public String Runtime;
    public String Language;
    public String Rated;
    public String Production;
    public String Released;
    public String Plot;
    public String Director;
    public String Title;
    public String Actors;
    public String Response;
    public String Type;
    public String Awards;
    public String DVD;
    public String Year;
    public String Poster;
    public String Country;
    public String Genre;
    public String Writer;
    public String Error;
    public String Name;
    public String imdbVotes;
    public String imdbID;
    public String imdbRating;
    public String totalSeasons;
    public Boolean isLiked = false;

    @Override
    public String toString() {
        return "Movie{" +
                "Metascore='" + Metascore + '\'' +
                ", BoxOffice='" + BoxOffice + '\'' +
                ", Website='" + Website + '\'' +
                ", Ratings=" + Ratings +
                ", Runtime='" + Runtime + '\'' +
                ", Language='" + Language + '\'' +
                ", Rated='" + Rated + '\'' +
                ", Production='" + Production + '\'' +
                ", Released='" + Released + '\'' +
                ", Plot='" + Plot + '\'' +
                ", Director='" + Director + '\'' +
                ", Title='" + Title + '\'' +
                ", Actors='" + Actors + '\'' +
                ", Response='" + Response + '\'' +
                ", Type='" + Type + '\'' +
                ", Awards='" + Awards + '\'' +
                ", DVD='" + DVD + '\'' +
                ", Year='" + Year + '\'' +
                ", Poster='" + Poster + '\'' +
                ", Country='" + Country + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Writer='" + Writer + '\'' +
                ", Error='" + Error + '\'' +
                ", Name='" + Name + '\'' +
                ", imdbVotes='" + imdbVotes + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", totalSeasons='" + totalSeasons + '\'' +
                ", isLiked=" + isLiked +
                '}';
    }

    public Movie() {
    }
}
