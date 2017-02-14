package com.fana.caribuku.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fana.caribuku.adapter.CustomGrid;
import com.fana.caribuku.adapter.ExpandableHeightGridView;
import com.fana.caribuku.R;
import com.fana.caribuku.fragment.Fragment_Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HalamanDepan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener,Fragment_Search.OnFragmentInteractionListener {

    GridView grid;
    public String query;
    private String TAG;
    Fragment fragment_search = new Fragment_Search();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //membuat arraylist baru untuk menampung data dari internet
        final ArrayList<String> judul_buku = new ArrayList<>();
        final ArrayList<String> gambar_buku = new ArrayList<>();
        final ArrayList<String> penerbit_buku = new ArrayList<>();
        final ArrayList<String> terbit_buku = new ArrayList<>();
        final ArrayList<String[]> pengarang_buku = new ArrayList<>();

        /* Request data dari internet (json) menggunakan Volley library start */
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        //url berupa json (biasanya jsonArray)
        String url = "https://raw.githubusercontent.com/SlexAxton/books.jquery.com/master/books/jquery-amazon-books.json";
//        String url = "https://raw.githubusercontent.com/tamingtext/book/master/apache-solr/example/exampledocs/books.json";
//        String url = "http://api.androidhive.info/feed/feed.json";
        //membuat string request dari url
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //respon berupa string s
                try {
//                    String author = "";
                    //dari string s diubah ke jsonArray
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0;i<jsonArray.length();i++) {
                        //masing2 jsonObject di jsonArray diambil variable "item"
                        //jadi didalam jsonArray itu terdapat beberapa jsonObject
                        JSONObject item = jsonArray.getJSONObject(i);
                        //memasukkan data dari jsonObject ke ArrayList yang dibuat
                        //perbedaan getString dan optString :
                        // jika optString ketika key yg diambil tidak ada maka akan diisi dengan "",
                        // jika getString ketika key yg diambil tidak ada maka akan throwError
                        judul_buku.add(i, item.getString("Title"));
                        penerbit_buku.add(i,item.optString("Publisher"));
                        terbit_buku.add(i,item.optString("Published"));
                        gambar_buku.add(i, item.optString("Image"));

                        if (item.has("Author")){
                            JSONArray authorArray = item.getJSONArray("Author");
                            String[] authors = new String[authorArray.length()];
                            for (int x = 0;x<authorArray.length();x++){
                                 authors[x] = authorArray.getString(x);
                                }
                            pengarang_buku.add(i,authors);
                            } else {
                                String[] authorNull = new String[1];
                                authorNull[0] = "-";
                                pengarang_buku.add(i,authorNull);
                            }
                        }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }

                //konversi dari arraylist ke array
                String[] web = new String[judul_buku.size()];
                String[] imageUrl = new String[gambar_buku.size()];
                web = judul_buku.toArray(web);
                imageUrl = gambar_buku.toArray(imageUrl);

                //mengirim data (judul, dan url gambar) ke GridView
                CustomGrid adapter = new CustomGrid(HalamanDepan.this, web, imageUrl);
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
                        //kirim value ke activity detailBuku
                        intent.putExtra("buku_nama", judul_buku.get(+position));
                        intent.putExtra("buku_id_gambar", gambar_buku.get(+position));
                        intent.putExtra("erbit", penerbit_buku.get(+position));
                        intent.putExtra("buku_terbit", terbit_buku.get(+position));
                        intent.putExtra("buku_pengarang", pengarang_buku.get(+position));
                        //intent.putParcelableArrayListExtra("buku_pengarang", pengarang_buku.get(+position))
                        startActivity(intent);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(stringRequest);


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
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        //mengirim query ke fragment search
        Bundle bundle = new Bundle();
        bundle.putString("query",query);
        if (fragment_search.getArguments() != null){
            fragment_search.getArguments().clear();
            fragment_search.getArguments().putString("query",query);
        } else {
            fragment_search.setArguments(bundle);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment_container,fragment_search);
        fragmentTransaction.commit();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //sudah dihandle di onQueryTextSubmit
        // User changed the text
        /*Bundle bundle = new Bundle();
        bundle.putString("query",newText);
        Fragment fragment_search = new Fragment_Search();
        fragment_search.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment_container,fragment_search);
        fragmentTransaction.commit();*/
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment_search);
        fragmentTransaction.commit();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final View sv_content_main =  findViewById(R.id.sv_content_main);
        final View fl_fragment_container = findViewById(R.id.fl_fragment_container);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        //searchItem.expandActionView();
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(HalamanDepan.this, "expand", Toast.LENGTH_SHORT).show();
                onQueryTextChange(null);
                //ketika klik tombol search konten utama disembunyikan diganti fragmen
                sv_content_main.setVisibility(View.GONE);
                fl_fragment_container.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(HalamanDepan.this, "collapse", Toast.LENGTH_SHORT).show();
                //ketika klik back fragmen disembunyikan diganti konten utama
                sv_content_main.setVisibility(View.VISIBLE);
                fl_fragment_container.setVisibility(View.GONE);

//                Fragment fragment_search = new Fragment_Search();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragment_search);
                fragmentTransaction.commit();

                return true;
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
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
            Intent intent = new Intent(HalamanDepan.this,LoginActivity.class);
            startActivity(intent);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
