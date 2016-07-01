package com.fana.caribuku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.fana.caribuku.R;
import com.fana.caribuku.singleton.VolleySingleton;

public class Keranjang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //instansiasi objek di xml
        TextView tv_keranjang_judul = (TextView) findViewById(R.id.tv_keranjang_judul);
        NetworkImageView iv_keranjang_gambar = (NetworkImageView) findViewById(R.id.iv_keranjang_buku);
        Button bt_keranjang_submit  = (Button) findViewById(R.id.bt_keranjang_submit);

        //ambil data
        Intent intent = getIntent();
        String judul = intent.getStringExtra("buku_nama");
        String idGambar = intent.getStringExtra("buku_id_gambar");

        //set value
        tv_keranjang_judul.setText(judul);
        iv_keranjang_gambar.setImageUrl(idGambar, VolleySingleton.getInstance(getApplicationContext()).getImageLoader());

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


        bt_keranjang_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Keranjang.this,Bayar.class);
                //intent.putExtra()
                startActivity(intent);
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
