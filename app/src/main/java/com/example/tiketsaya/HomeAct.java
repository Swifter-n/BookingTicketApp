package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {
    LinearLayout btn_ticket_1, btn_ticket_2, btn_ticket_3,btn_ticket_4,btn_ticket_5,btn_ticket_6;
    CircleView btn_pic_home;
    TextView fullname, hobby, user_balance,text_1,text_2,text_3,text_4,text_5,text_6;
    ImageView photo_home_user, image_1,image_2,image_3,image_4,image_5,image_6;
    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUsernameLocal();

        btn_ticket_1 = findViewById(R.id.btn_ticket_1);
        btn_ticket_2 = findViewById(R.id.btn_ticket_2);
        btn_ticket_3 = findViewById(R.id.btn_ticket_3);
        btn_ticket_4 = findViewById(R.id.btn_ticket_4);
        btn_ticket_5 = findViewById(R.id.btn_ticket_5);
        btn_ticket_6 = findViewById(R.id.btn_ticket_6);
        image_1 = findViewById(R.id.image_1);
        image_2 = findViewById(R.id.image_2);
        image_3 = findViewById(R.id.image_3);
        image_4 = findViewById(R.id.image_4);
        image_5 = findViewById(R.id.image_5);
        image_6 = findViewById(R.id.image_6);
        text_1 = findViewById(R.id.text_1);
        text_2 = findViewById(R.id.text_2);
        text_3 = findViewById(R.id.text_3);
        text_4 = findViewById(R.id.text_4);
        text_5 = findViewById(R.id.text_5);
        text_6 = findViewById(R.id.text_6);


        fullname = findViewById(R.id.fullname);
        hobby = findViewById(R.id.hobby);
        user_balance = findViewById(R.id.user_balance);
        photo_home_user = findViewById(R.id.photo_home_user);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fullname.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                hobby.setText(dataSnapshot.child("hobby").getValue().toString());
                user_balance.setText("$ "+ dataSnapshot.child("user_balance").getValue().toString() + "");
                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop().fit().into(photo_home_user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btn_ticket_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetail = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotoDetail.putExtra("jenis_tiket", "Pisa");
                startActivity(gotoDetail);

            }
        });
        btn_ticket_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetail = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotoDetail.putExtra("jenis_tiket", "Torri");
                startActivity(gotoDetail);

            }
        });
        btn_ticket_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetail = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotoDetail.putExtra("jenis_tiket", "Pagoda");
                startActivity(gotoDetail);

            }
        });
        btn_ticket_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetail = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotoDetail.putExtra("jenis_tiket", "Candi");
                startActivity(gotoDetail);

            }
        });
        btn_ticket_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetail = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotoDetail.putExtra("jenis_tiket", "Sphinx");
                startActivity(gotoDetail);

            }
        });
        btn_ticket_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetail = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotoDetail.putExtra("jenis_tiket", "Monas");
                startActivity(gotoDetail);

            }
        });


        btn_pic_home = findViewById(R.id.btn_pic_home);
        btn_pic_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMyProfile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotoMyProfile);

            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
