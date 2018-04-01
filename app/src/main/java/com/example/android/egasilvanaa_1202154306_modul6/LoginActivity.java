package com.example.android.egasilvanaa_1202154306_modul6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //deklarasi variabel untuk Edittext dan firebase
    private EditText username, password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inisiasi edittext dan firebase dari variabel yang sudah di set
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    //method untuk aksi batal
    public void Daftar(View view) {
        //dilakukan penyimpanan email dan password untuk login
        (firebaseAuth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString())).addOnCompleteListener
                (new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){ //jika berhasil maka menampilkan toast berhasil
                    Toast.makeText(LoginActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                }
                else {
                    //jika tidak berhasil maka muncul toast message error
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    //method untuk melakukan login
    public void Login(View view) {
        //melakukan pengecekan terhadap user yang sudah terdaftar pada firebase
        (firebaseAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){ //jika login berhasil maka menampilkan toas berhasil
                            Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }else{ //jika tidak berhasil maka muncul toast message error
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
