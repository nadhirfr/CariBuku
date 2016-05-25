package com.fana.caribuku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fana.caribuku.Adapter.CustomGrid;
import com.fana.caribuku.Adapter.ExpandableHeightGridView;

public class HalamanDepan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView grid;
    String[] web = {
            "Buku Satu",
            "Buku Dua",
            "Buku Tiga",
            "Buku Empat",
            "Buku Lima",
            "Buku Enam",
            "Buku Tujug",
            "Buku Delapan",
            "Buku Sembilan",
            "Buku Sepuluh",
            "Buku Sebelas",
            "Buku Duabelas",
            "Buku Sembilan",
            "Buku Sepuluh",
            "Buku Sebelas",
            "Buku Duabelas"

    } ;
    int[] imageId = {
            R.drawable.inggris,
            R.drawable.bahasa,
            R.drawable.matematika,
            R.drawable.pai,
            R.drawable.inggris,
            R.drawable.bahasa,
            R.drawable.matematika,
            R.drawable.pai,
            R.drawable.inggris,
            R.drawable.bahasa,
            R.drawable.matematika,
            R.drawable.pai,
            R.drawable.inggris,
            R.drawable.bahasa,
            R.drawable.matematika,
            R.drawable.pai

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CustomGrid adapter = new CustomGrid(HalamanDepan.this, web, imageId);
        ExpandableHeightGridView grid= (ExpandableHeightGridView) findViewById(R.id.gv_items);
//        grid.setVerticalScrollBarEnabled(false);
        grid.setFocusable(false);
        grid.setAdapter(adapter);
        grid.setExpanded(true);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(HalamanDepan.this,DetailBuku.class);
                //harusnya diambil dari database
                intent.putExtra("buku_nama",web[+ position]);
                intent.putExtra("buku_id_gambar",imageId[+ position]);
                startActivity(intent);

                //Toast.makeText(HalamanDepan.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
