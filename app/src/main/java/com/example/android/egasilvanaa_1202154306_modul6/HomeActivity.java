package com.example.android.egasilvanaa_1202154306_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private ListView lv;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inisiasi listview
        lv = findViewById(R.id.listViewImage);

        //inisiasi tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //set judul tab
        tabLayout.addTab(tabLayout.newTab().setText("TERBARU"));
        tabLayout.addTab(tabLayout.newTab().setText("GALLERY SAYA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //tombol untuk melakukan aksi
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //untuk berpindah ke halaman selanjutnya
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                //memulai berpindah
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu_main) {
        //menampilkan menu logout
        getMenuInflater().inflate(R.menu.menu, menu_main);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //method untuk logout
    public void LogOut(MenuItem item) {
        //kembali ke halaman login
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        //memulai intent
        startActivity(intent);
    }
}
