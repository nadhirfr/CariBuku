package com.fana.caribuku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Keranjang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ini spiner_jumlah
        Spinner dropdown_jumlah = (Spinner)findViewById(R.id.spinner_jumlah);
        String[] items_jumlah = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter_jumlah = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_jumlah);
        dropdown_jumlah.setAdapter(adapter_jumlah);

        // ini spiner_kirim
        Spinner dropdown_kirim = (Spinner)findViewById(R.id.spinner_kirim);
        String[] items_kirim = new String[]{"Ambil Sendiri", "JNE", "GO-JEK"};
        ArrayAdapter<String> adapter_kirim = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_kirim);
        dropdown_kirim.setAdapter(adapter_kirim);

        // ini spiner_paket
        Spinner dropdown_paket = (Spinner)findViewById(R.id.spinner_paket);
        String[] items_paket = new String[]{"", "Transfer"};
        ArrayAdapter<String> adapter_paket = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_paket);
        dropdown_paket.setAdapter(adapter_paket);

        Intent intent = getIntent();
        String judul = intent.getStringExtra("buku_nama");
        int idGambar = intent.getIntExtra("buku_id_gambar", 0);

        TextView tv_keranjang_judul = (TextView) findViewById(R.id.tv_keranjang_judul);
        tv_keranjang_judul.setText(judul);
        ImageView iv_keranjang_gambar = (ImageView) findViewById(R.id.iv_keranjang_buku);
        iv_keranjang_gambar.setImageResource(idGambar);


    }

}
