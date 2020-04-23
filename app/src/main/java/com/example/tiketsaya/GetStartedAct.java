package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedAct extends AppCompatActivity {
    Animation ttb, app_splash_subtitle;
    Button btn_signIn;
    Button btn_create_account;
    ImageView imageGetStarted;
    TextView textGetStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        app_splash_subtitle = AnimationUtils.loadAnimation(this, R.anim.app_splash_subtitle);

        imageGetStarted = findViewById(R.id.imageGetStarted);
        textGetStarted = findViewById(R.id.textGetstarted);

        imageGetStarted.startAnimation(ttb);
        textGetStarted.startAnimation(ttb);


        btn_signIn = findViewById(R.id.btn_signIn);
        btn_create_account =findViewById(R.id.btn_create_account);

        btn_signIn.startAnimation(app_splash_subtitle);
        btn_create_account.startAnimation(app_splash_subtitle);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(signIn);

            }
        });

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocreateAccount = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gotocreateAccount);

            }
        });
    }
}
