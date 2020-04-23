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

public class SuccessRegisterAct extends AppCompatActivity {
    Animation app_splash, app_splash_subtitle, ttb;
    Button explore;
    ImageView icon_app;
    TextView app_title;
    TextView app_subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        app_splash_subtitle = AnimationUtils.loadAnimation(this, R.anim.app_splash_subtitle);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        explore = findViewById(R.id.explore);
        icon_app = findViewById(R.id.icon_app);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        icon_app.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);
        explore.startAnimation(app_splash_subtitle);


        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(SuccessRegisterAct.this, HomeAct.class);
                startActivity(gotoHome);
                finish();
            }
        });
    }
}
