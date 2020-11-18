package com.example.movierecommendation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class GenreActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[] buttons = new Button[10];
    private int[] buttonIds = {R.id.btn_action, R.id.btn_adventure, R.id.btn_comedy,
            R.id.btn_crime, R.id.btn_drama, R.id.btn_fantasy, R.id.btn_horror,
            R.id.btn_romance, R.id.btn_scifi, R.id.btn_thriller};
    private Map<Integer, Boolean> isSelected = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(this);
            isSelected.put(buttonIds[i], false);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        Button clickedButton = findViewById(view.getId());

        //clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        if (isSelected.get(view.getId())) {
            clickedButton.getBackground().getCurrent().setTint(ContextCompat.getColor(this, R.color.blue));
            isSelected.put(view.getId(), false);
        } else {
            clickedButton.getBackground().getCurrent().setTint(ContextCompat.getColor(this, R.color.green));
            isSelected.put(view.getId(), true);
        }
    }
}