package com.fana.caribuku.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fana.caribuku.R;

public class Konfirmasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);
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

        // ini spiner_bank
        Spinner dropdown_bank = (Spinner)findViewById(R.id.spinner_konfirmasi_bank);
        String[] items_bank = new String[]{"BCA", "BNI"};
        ArrayAdapter<String> adapter_jumlah = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_bank);
        dropdown_bank.setAdapter(adapter_jumlah);

    }

}