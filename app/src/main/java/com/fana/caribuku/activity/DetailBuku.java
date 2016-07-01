package com.fana.caribuku.activity;

import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fana.caribuku.R;
import com.fana.caribuku.singleton.VolleySingleton;

import java.util.ArrayList;

public class DetailBuku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //instansiasi objek di layout
        TextView tv_terbit = (TextView) findViewById(R.id.tv_detail_tahun_terbit);
        TextView tv_penerbit = (TextView) findViewById(R.id.tv_detail_penerbit);
        TextView tv_pengarang = (TextView) findViewById(R.id.tv_detail_pengarang);
        TextView tv = (TextView) findViewById(R.id.detailbuku_judul) ;
        NetworkImageView iv = (NetworkImageView) findViewById(R.id.detailbuku_gambar);

        //ambil data yang dikirim
        Intent intent = getIntent();
        final String judul = intent.getStringExtra("buku_nama");
        final String idImage = intent.getStringExtra("buku_id_gambar");
        final String penerbit = intent.getStringExtra("buku_penerbit");
        final String terbit = intent.getStringExtra("buku_terbit");
        final String[] pengarangs = intent.getStringArrayExtra("buku_pengarang");
        String pengarang = "";
        if(pengarangs != null){
            for (int i = 0 ;i<pengarangs.length;i++){
                String comma = "";
                if (i!=0 ) comma +="\n";
                pengarang = pengarang + comma + pengarangs[i];
            }
        }

        //set data yg tadi diambil
        tv.setText(judul);
        tv_penerbit.setText(penerbit);
        tv_terbit.setText(terbit);
        tv_pengarang.setText(pengarang);
        iv.setImageUrl(idImage, VolleySingleton.getInstance(this.getApplicationContext()).getImageLoader());

        Button beli = (Button) findViewById(R.id.detailbuku_beli);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ini untuk mengirim data ke Keranjang setelah tombol di klik
                Intent intent = new Intent(DetailBuku.this,Keranjang.class);
                intent.putExtra("buku_nama",judul);
                intent.putExtra("buku_id_gambar",idImage);
                intent.putExtra("buku_penerbit", penerbit);
                intent.putExtra("buku_terbit", terbit);
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
