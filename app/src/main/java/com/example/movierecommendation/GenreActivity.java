package com.example.movierecommendation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class GenreActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDone;
    Button btnSkip;

    private CardView[] cards = new CardView[6];
    private int[] cardIds = {R.id.cardAction, R.id.cardComedy, R.id.cardDrama,
            R.id.cardSciFi, R.id.cardThriller, R.id.cardRomantic};
    private Map<Integer, Boolean> isSelected = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        btnDone = findViewById(R.id.btnDone);
        btnSkip = findViewById(R.id.btnSkip);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        for (int i = 0; i < cards.length; i++) {
            cards[i] = findViewById(cardIds[i]);
            cards[i].setOnClickListener(this);
            isSelected.put(cardIds[i], false);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        CardView clickedCard = findViewById(view.getId());

        //clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        if (isSelected.get(view.getId())) {
            clickedCard.getBackground().getCurrent().setTint(ContextCompat.getColor(this, R.color.blue));
            isSelected.put(view.getId(), false);
        } else {
            clickedCard.getBackground().getCurrent().setTint(ContextCompat.getColor(this,
                    R.color.white));
            isSelected.put(view.getId(), true);
        }
    }
}