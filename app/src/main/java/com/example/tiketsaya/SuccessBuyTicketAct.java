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

public class SuccessBuyTicketAct extends AppCompatActivity {
    Animation app_splash, app_splash_subtitle, ttb;
    Button btn_view_ticket, btn_my_dashboard;
    TextView appTitle, appSubtitle;
    ImageView app_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        appTitle = findViewById(R.id.appTitle);
        appSubtitle = findViewById(R.id.appSubtitle);
        app_icon = findViewById(R.id.app_icon);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        app_splash_subtitle = AnimationUtils.loadAnimation(this, R.anim.app_splash_subtitle);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        app_icon.startAnimation(app_splash);
        appTitle.startAnimation(ttb);
        appSubtitle.startAnimation(ttb);
        btn_view_ticket.startAnimation(app_splash_subtitle);
        btn_my_dashboard.startAnimation(app_splash_subtitle);

        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(SuccessBuyTicketAct.this, HomeAct.class);
                startActivity(gotoHome);
                finish();
            }
        });

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMyTickets = new Intent(SuccessBuyTicketAct.this, MyTicketAct.class);
                startActivity(gotoMyTickets);
                finish();
            }
        });



    }
}
