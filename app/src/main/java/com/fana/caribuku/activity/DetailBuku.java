package com.fana.caribuku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fana.caribuku.R;

public class DetailBuku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String judul = intent.getStringExtra("buku_nama");
        final int idImage = intent.getIntExtra("buku_id_gambar",0);

        TextView tv = (TextView) findViewById(R.id.detailbuku_judul) ;
        ImageView iv = (ImageView) findViewById(R.id.detailbuku_gambar);
        tv.setText(judul);
        iv.setImageResource(idImage);

        Button beli = (Button) findViewById(R.id.detailbuku_beli);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBuku.this,Keranjang.class);
                intent.putExtra("buku_nama",judul);
                intent.putExtra("buku_id_gambar",idImage);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
