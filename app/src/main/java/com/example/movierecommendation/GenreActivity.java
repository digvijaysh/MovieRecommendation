package com.example.movierecommendation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenreActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDone;
    Button btnSkip;
    Set<String> genre;
    GoogleSignInAccount account;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    CollectionReference collectionReference;
    FirebaseFirestore firebaseFirestore;


    private CardView[] cards = new CardView[6];
    private int[] cardIds = {R.id.cardAction, R.id.cardComedy, R.id.cardDrama,
            R.id.cardSciFi, R.id.cardThriller, R.id.cardRomantic};
    private Map<Integer, Boolean> isSelected = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        account = GoogleSignIn.getLastSignedInAccount(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("users");
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        genre = new HashSet<>();
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGenreInformation();
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
            int id = view.getId();
            clickedCard.getBackground().getCurrent().setTint(ContextCompat.getColor(this, R.color.blue));

            if (id == R.id.cardAction) {
                genre.add("Action");
            } else if (id == R.id.cardComedy) {
                genre.add("Comedy");
            } else if (id == R.id.cardDrama) {
                genre.add("Drama");
            } else if (id == R.id.cardRomantic) {
                genre.add("Romance");
            } else if (id == R.id.cardThriller) {
                genre.add("Thriller");
            } else if (id == R.id.cardSciFi) {
                genre.add("Sci-Fi");
            }
            isSelected.put(view.getId(), false);
        } else {
            clickedCard.getBackground().getCurrent().setTint(ContextCompat.getColor(this,
                    R.color.white));
            int id = view.getId();
            if (id == R.id.cardAction) {
                genre.remove("Action");
            } else if (id == R.id.cardComedy) {
                genre.remove("Comedy");
            } else if (id == R.id.cardDrama) {
                genre.remove("Drama");
            } else if (id == R.id.cardRomantic) {
                genre.remove("Romance");
            } else if (id == R.id.cardThriller) {
                genre.remove("Thriller");
            } else if (id == R.id.cardSciFi) {
                genre.remove("Sci-Fi");
            }
            isSelected.put(view.getId(), true);
        }
        System.out.println(genre);
    }

    public void saveGenreInformation() {
        String email = account == null ? FirebaseAuth.getInstance().getCurrentUser().getEmail() : account.getEmail();
        Log.d("MainActivity", email);
        collectionReference
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() == 0) {
                            String id = collectionReference.document().getId();
                            List<String> list = new ArrayList<>(genre);
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", email);
                            user.put("liked", new ArrayList<>());
                            user.put("genre", list);
                            collectionReference.document(id).set(user)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(GenreActivity.this, "Saved", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(GenreActivity.this, "Error", Toast.LENGTH_SHORT).show());
                        }
                    }
                });
    }
}