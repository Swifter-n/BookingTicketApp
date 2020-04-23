package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView create_account;
    Button btn_signIn;
    EditText xusername, xpassword;
    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        btn_signIn = findViewById(R.id.btn_signIn);





        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_signIn.setEnabled(false);
                btn_signIn.setText("Loading...");

               final String username = xusername.getText().toString();
               final String password = xpassword.getText().toString();
                if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Kosong", Toast.LENGTH_SHORT).show();
                    btn_signIn.setEnabled(true);
                    btn_signIn.setText("SIGN IN");
                }else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password Kosong", Toast.LENGTH_SHORT).show();
                    btn_signIn.setEnabled(true);
                    btn_signIn.setText("SIGN IN");
                }else{
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                String passwordFirebase = dataSnapshot.child("password").getValue().toString();
                                if(password.equals(passwordFirebase)){
                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key, xusername.getText().toString());
                                    editor.apply();

                                    Intent gotoHome = new Intent(SignInAct.this, HomeAct.class);
                                    startActivity(gotoHome);
                                    finish();

                                }else{
                                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_SHORT).show();
                                    btn_signIn.setEnabled(true);
                                    btn_signIn.setText("SIGN IN");
                                }

                            }else {
                                Toast.makeText(getApplicationContext(), "Username is wrong", Toast.LENGTH_SHORT).show();
                                btn_signIn.setEnabled(true);
                                btn_signIn.setText("SIGN IN");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Database Errors", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });

        create_account = findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterOne = new Intent(SignInAct.this, RegisterOneAct.class);
                startActivity(gotoregisterOne);
                finish();
            }
        });

    }
}
