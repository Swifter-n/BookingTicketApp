package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {
    LinearLayout back;
    Button btn_continue, btn_image;
    EditText nama_lengkap, hobby;
    ImageView pic_registasi_user;
    DatabaseReference reference;
    StorageReference storage;
    Uri photo_location;
    Integer photo_max = 1;

    String USERNAME_KEY = "username_key";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);



        get_usernameLocal();

        btn_continue = findViewById(R.id.btn_continue);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        hobby = findViewById(R.id.hobby);
        pic_registasi_user = findViewById(R.id.pic_registasi_user);
        btn_image = findViewById(R.id.btn_image);


        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading...");

                final String xnama_lengkap = nama_lengkap.getText().toString();
                final String xhobby = hobby.getText().toString();
                if(xnama_lengkap.isEmpty()){
                    Toast.makeText(getApplicationContext(), "FullName Kosong", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }else if(xhobby.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Hobby Kosong", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }else{
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                    storage = FirebaseStorage.getInstance().getReference().child("Photouser").child(username_key_new);

                    if(photo_location != null){
                        final StorageReference storageReference = storage.child(System.currentTimeMillis() + " . " + getFileExtension(photo_location));
                        storageReference.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url_photo = uri.toString();
                                        reference.getRef().child("url_photo_profile").setValue(url_photo);
                                        reference.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                                        reference.getRef().child("hobby").setValue(hobby.getText().toString());
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        Intent gotoSuccessRegister = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                                        startActivity(gotoSuccessRegister);
                                        finish();
                                    }
                                });

                            }
                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            }
                        });
                    }
                }



            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoPrev = new Intent(RegisterTwoAct.this, RegisterOneAct.class);
                startActivity(backtoPrev);
                finish();
            }
        });

    }
    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void get_usernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null){
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_registasi_user);
        }
    }
}
