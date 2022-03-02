package com.example.apifun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView;
    private ImageView profileImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        getSupportActionBar().setTitle("Profile");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //references
        nameTextView = findViewById(R.id.name_text_view);
        profileImageView = findViewById(R.id.profile_image_view);
        progressBar = findViewById(R.id.progressBar);


        GoogleSignInAccount user = GoogleSignIn.getLastSignedInAccount(this);
        if(user!=null){
            Picasso.get().load(user.getPhotoUrl()).into(profileImageView, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Failed to Load user Data", Toast.LENGTH_SHORT).show();
                }
            });
            nameTextView.setText(user.getId());
        }

    }

}