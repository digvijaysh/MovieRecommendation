package com.example.movierecommendation.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
import com.squareup.picasso.Transformation;

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
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ((MainActivity)getActivity()).finish();
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



    public class CircleTransform implements Transformation
    {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }


        @Override
        public String key() {
            return "circle";
        }

        public Bitmap getCroppedBitmap(Bitmap bitmap) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                    bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
            //return _bmp;
            return output;
        }
    }
}