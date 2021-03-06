package com.example.movierecommendation.model;

import java.util.List;

public class User {
    public String id;
    public String email;
    public List<String> liked = null;
    public List<String> genre = null;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", liked=" + liked +
                ", genre=" + genre +
                '}';
    }
}
