package com.fana.caribuku.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.fana.caribuku.Adapter.CustomGrid;
import com.fana.caribuku.Adapter.ExpandableHeightGridView;
import com.fana.caribuku.R;

import java.util.Arrays;

public class Search extends AppCompatActivity
        implements TextWatcher{
    String[] text = {};
    int[] image = {};
    public int j = 0;

    EditText edSearch;
    TextView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate widget
        edSearch = (EditText) findViewById(R.id.txt_search);
        btnClose = (TextView) findViewById(R.id.close);


        Intent intent = getIntent();
        String[] web = intent.getStringArrayExtra("all_text");
        int[] imageId = intent.getIntArrayExtra("all_image");
        String query = intent.getStringExtra("keyword");


        for (int i = 0; i<web.length; i++){

            if (web[i].toLowerCase().indexOf(query.toLowerCase())!= -1){
                text[j] = web[i];
                image[j] = imageId[i];
                j++;
            }else{
                text = web;
                image = imageId;
            }
        }
        CustomGrid adapter = new CustomGrid(Search.this, text, image);
        ExpandableHeightGridView grid= (ExpandableHeightGridView) findViewById(R.id.gv_items_search);
//        grid.setVerticalScrollBarEnabled(false);
        grid.setFocusable(false);
        grid.setAdapter(adapter);
        grid.setExpanded(true);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(Search.this,DetailBuku.class);
                //harusnya diambil dari database
                intent.putExtra("buku_nama",text[+ position]);
                intent.putExtra("buku_id_gambar",image[+ position]);
                startActivity(intent);

                //Toast.makeText(HalamanDepan.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    doSearch(edSearch.getText().toString());
                    handled = true;
                }
                //sembunyikan virtual keyboard setelah melakukan pencarian
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
                return handled;
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /*if (edSearch.getText().toString().isEmpty()) {
            clearText.setVisibility(View.GONE);
        }else{
            clearText.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /*if (search.getText().toString().isEmpty()) {
            clearText.setVisibility(View.GONE);
        }else{
            clearText.setVisibility(View.VISIBLE);
        }*/
        // ketika text berubah kasih logik bisa hide / visible
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     *
     * @param search
     */
    private void doSearch(String search){
        // lakukan proses search disini
    }


}
