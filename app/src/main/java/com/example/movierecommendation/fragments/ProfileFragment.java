package com.example.movierecommendation.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movierecommendation.LoginActivity;
import com.example.movierecommendation.MainActivity;
import com.example.movierecommendation.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;


public class ProfileFragment extends Fragment {

    Button logout;
    ImageView image;
    TextView tUsername;
    String username;
    String photoURL;
    FragmentTransaction fragmentTransaction;
    GoogleSignInAccount account;
    FirebaseUser firebaseUser;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = GoogleSignIn.getLastSignedInAccount(getContext());
       // Toast.makeText(getContext(),account.getEmail(),Toast.LENGTH_SHORT).show();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions.DEFAULT_SIGN_IN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logout = view.findViewById(R.id.logout);
        image = view.findViewById(R.id.profilePicture);
        tUsername = view.findViewById(R.id.userEmail);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        username = account == null ? firebaseUser.getEmail() : account.getEmail();
        photoURL = account == null ? null : account.getPhotoUrl().toString();
        tUsername.setText(username);
        if(photoURL!= null) {
            Picasso.get().load(photoURL).into(image);
        }
        else{
            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile));
        }
        return view;
    }

    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            FirebaseAuth.getInstance().signOut();
                        }
                    }
                });
    }

}