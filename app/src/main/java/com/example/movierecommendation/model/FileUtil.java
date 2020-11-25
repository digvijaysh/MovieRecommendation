package com.example.movierecommendation.model;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.movierecommendation.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

/**
 * FileUtil class to load data from asset files.
 */
public class FileUtil {

    private FileUtil() {
    }

    /**
     * Load TF Lite model from asset file.
     */
    public static MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath)
            throws IOException {
        try (AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
             FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor())) {
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        }
    }

    /**
     * Load candidates from asset file.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Collection<MovieItem> loadMovieList(
            AssetManager assetManager, String candidateListPath) throws IOException {
        String content = loadFileContent(assetManager, candidateListPath);
        Gson gson = new Gson();
        Type type = new TypeToken<Collection<MovieItem>>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    /**
     * Load config from asset file.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Config loadConfig(AssetManager assetManager, String configPath) throws IOException {
        String content = loadFileContent(assetManager, configPath);
        Gson gson = new Gson();
        Type type = new TypeToken<Config>() {
        }.getType();
        return gson.fromJson(content, type);
    }

    /**
     * Load file content from asset file.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("AndroidJdkLibsChecker")
    private static String loadFileContent(AssetManager assetManager, String path) throws IOException {
        try (InputStream ins = assetManager.open(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(ins))) {
            return reader.lines().collect(joining(System.lineSeparator()));
        }
    }
}
