package com.example.movierecommendation;

/** Config for recommendation app. */
public final class Config {
  private static final String DEFAULT_MODEL_PATH = "recommendation_rnn_i10o100.tflite";
  private static final String DEFAULT_MOVIE_LIST_PATH = "sorted_movie_vocab.json";
  private static final int DEFAULT_INPUT_LENGTH = 10;
  private static final int DEFAULT_OUTPUT_LENGTH = 100;
  private static final int DEFAULT_TOP_K = 10;
  private static final int PAD_ID = 0;
  private static final int DEFAULT_OUTPUT_IDS_INDEX = 0;
  private static final int DEFAULT_OUTPUT_SCORES_INDEX = 1;
  private static final int DEFAULT_FAVORITE_LIST_SIZE = 100;

  /** TF Lite model path. */
  public String modelPath = DEFAULT_MODEL_PATH;
  /** Number of input ids from the model. */
  public int inputLength = DEFAULT_INPUT_LENGTH;
  /** Number of output length from the model. */
  public int outputLength = DEFAULT_OUTPUT_LENGTH;
  /** Number of max results to show in the UI. */
  public int topK = DEFAULT_TOP_K;
  /** Path to the movie list. */
  public String movieListPath = DEFAULT_MOVIE_LIST_PATH;

  /** Id for padding. */
  public int pad = PAD_ID;
  /** Output index for ID. */
  public int outputIdsIndex = DEFAULT_OUTPUT_IDS_INDEX;
  /** Output index for score. */
  public int outputScoresIndex = DEFAULT_OUTPUT_SCORES_INDEX;

  /** The number of favorite movies for users to choose from. */
  public int favoriteListSize = DEFAULT_FAVORITE_LIST_SIZE;

  public Config() {}
}
