package com.example.movierecommendation.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/** A movie item representing recommended content. */
public class MovieItem {

  public static final String JOINER = " | ";
  public static final String DELIMITER_REGEX = "[|]";

  public final int id;
  public final String title;
  public final List<String> genres;
  public final int count;

  public boolean selected = false; // For UI selection. Default item is not selected.

  private MovieItem() {
    this(0, "", new ArrayList<>(), 0);
  }

  public MovieItem(int id, String title, List<String> genres, int count) {
    this.id = id;
    this.title = title;
    this.genres = genres;
    this.count = count;
  }

  @Override
  public String toString() {
    return String.format(
        "Id: %d, title: %s, genres: %s, count: %d, selected: %s",
        id, title, TextUtils.join(JOINER, genres), count, selected);
  }

}
