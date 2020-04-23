package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    LinearLayout back;
    Button btn_continue;
    EditText username, password, email_address;
    DatabaseReference reference, username_reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        btn_continue = findViewById(R.id.btn_continue);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading...");

                final String xusername = username.getText().toString();
                final String xpassword = password.getText().toString();
                final String xemail_address = email_address.getText().toString();

                if(xusername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username empty", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }else if(xpassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password empty", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }else if(xemail_address.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email Address empty", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }else {
                    username_reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                    username_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                Toast.makeText(getApplicationContext(), "Username sudah dipakai", Toast.LENGTH_SHORT).show();
                                btn_continue.setEnabled(true);
                                btn_continue.setText("CONTINUE");
                            }else{

                                //Local
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, username.getText().toString());
                                editor.apply();

                                //Firebase
                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                        dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                        dataSnapshot.getRef().child("user_balance").setValue(1000);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Intent gotoRegisterTwo = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                                startActivity(gotoRegisterTwo);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }



            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoSignIn = new Intent(RegisterOneAct.this, SignInAct.class);
                startActivity(backtoSignIn);
                finish();
            }
        });
    }
}
