package com.example.android.egasilvanaa_1202154306_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    //variabel yang dibutuhkan
    private ImageView imageView;
    private EditText title, desc;
    private Button choose;
    private Uri filepath;
    private final int PICK_IMAGE_REQUEST = 71;
    private static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";

    //variabel untuk firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //inisiasi firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //inisiasi referensi variabel
        imageView = (ImageView) findViewById(R.id.foto);
        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
        choose = (Button) findViewById(R.id.pilih);

        //klik tombol agar dapat memilih gambar dari penyimpanan
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST);

            }
        });
        //inisiasi toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //onClick method untuk mengupload gambar dan menyimpan ke dalam database
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filepath != null) {
                    //setting storage reference untuk mengambil gambar dan disimpan
                    StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                    ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //menampilkan toast jika berhasil upload
                            Toast.makeText(AddActivity.this, "Upload Successfull", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //menampilkan toast jika gagal upload
                            Toast.makeText(AddActivity.this, "Upload Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    //menampilkan toast untuk memilih gambar
                        Toast.makeText(AddActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

                    }
        }
    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //pengecekan data apakah yang diinputkan sesuai dan tidak nol
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filepath = data.getData();
            try{
                //mengambil gambar dari mediaStore
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                //set gambar yang sudah dipilih
                imageView.setImageBitmap(bitmap);
            }
            //menutup aktivitas
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void Tampil(View view) {
        //Intent untuk berpindah ke halaman selanjutnya
        Intent intent = new Intent(AddActivity.this, HomeActivity.class);
        //memulai perpindahan
        startActivity(intent);
    }
}
