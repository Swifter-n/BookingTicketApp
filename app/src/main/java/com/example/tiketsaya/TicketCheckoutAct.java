package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {
    Button btn_pay_now, btnMines, btnPlus;
    TextView textJumlahTicket, textTotalPrice, textMyBalance, nama_wisata, lokasi, short_tos_ticket;
    Integer valueJumlahTicket = 1;
    Integer MyBalance = 200;
    Integer valueTotalPrice;
    Integer valuePriceTicket = 0;
    ImageView noticePrice;
    Integer nomor_transaksi = new Random().nextInt();
    Integer sisa_balance = 0;
    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "username_key";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);
        getUsernameLocal();

        Bundle bundle =getIntent().getExtras();
       final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_pay_now = findViewById(R.id.btn_pay_now);
        btnMines = findViewById(R.id.btnMines);
        btnPlus = findViewById(R.id.btnPlus);
        textJumlahTicket = findViewById(R.id.textJumlahTicket);
        textMyBalance = findViewById(R.id.textMyBalance);
        textTotalPrice = findViewById(R.id.textTotalPrice);
        noticePrice = findViewById(R.id.noticePrice);
        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        short_tos_ticket = findViewById(R.id.short_tos_ticket);

        //Value Komponen Defaults
        textJumlahTicket.setText(valueJumlahTicket.toString());


        //Value Default, Hide Button Mines
        btnMines.animate().alpha(1).setDuration(300).start();
        btnMines.setEnabled(false);
        noticePrice.setVisibility(View.GONE);

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyBalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textMyBalance.setText("U$ "+ MyBalance +"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                short_tos_ticket.setText(dataSnapshot.child("ketentuan").getValue().toString());
                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();
                valuePriceTicket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());
                valueTotalPrice = valuePriceTicket * valueJumlahTicket;
                textTotalPrice.setText("US$ " +valueTotalPrice+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTicket += 1;
                textJumlahTicket.setText(valueJumlahTicket.toString());
                if(valueJumlahTicket > 1){
                    btnMines.animate().alpha(1).setDuration(300).start();
                    btnMines.setEnabled(true);
                }
                valueTotalPrice = valuePriceTicket * valueJumlahTicket;
                textTotalPrice.setText("US$ " +valueTotalPrice+"");
                if(valueTotalPrice > MyBalance){
                    btn_pay_now.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_pay_now.setEnabled(false);
                    textMyBalance.setTextColor(Color.parseColor("#D1206B"));
                    noticePrice.setVisibility(View.VISIBLE);
                }
            }
        });

        btnMines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTicket -= 1;
                textJumlahTicket.setText(valueJumlahTicket.toString());
                if(valueJumlahTicket < 2){
                    btnMines.animate().alpha(0).setDuration(300).start();
                    btnMines.setEnabled(false);
                }
                valueTotalPrice = valuePriceTicket * valueJumlahTicket;
                textTotalPrice.setText("US$ " +valueTotalPrice+"");
                if(valueTotalPrice < MyBalance){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay_now.setEnabled(true);
                    textMyBalance.setTextColor(Color.parseColor("#1ABC9C"));
                    noticePrice.setVisibility(View.GONE);
                }
            }
        });




        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(short_tos_ticket.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTicket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotoSuccessTicket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                        startActivity(gotoSuccessTicket);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = MyBalance - valuePriceTicket;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
