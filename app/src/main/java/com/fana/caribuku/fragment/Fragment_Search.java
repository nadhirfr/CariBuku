package com.fana.caribuku.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fana.caribuku.R;
import com.fana.caribuku.activity.DetailBuku;
import com.fana.caribuku.activity.HalamanDepan;
import com.fana.caribuku.adapter.CustomGrid;
import com.fana.caribuku.adapter.ExpandableHeightGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private String query;

    private OnFragmentInteractionListener mListener;

    public Fragment_Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Search newInstance(String param1, String param2) {
        Fragment_Search fragment = new Fragment_Search();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            query = getArguments().getString("query",null);
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        final View view = lf.inflate(R.layout.fragment__search, container, false); //pass the correct layout name for the fragment

        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final TextView tv_fragment_search = (TextView) view.findViewById(R.id.tv_fragment_search);
        final ArrayList<String> judul_buku = new ArrayList<>();
        final ArrayList<String> gambar_buku = new ArrayList<>();
        final ArrayList<String> penerbit_buku = new ArrayList<>();
        final ArrayList<String> terbit_buku = new ArrayList<>();
        final ArrayList<String[]> pengarang_buku = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://raw.githubusercontent.com/SlexAxton/books.jquery.com/master/books/jquery-amazon-books.json";
//        String url = "https://raw.githubusercontent.com/tamingtext/book/master/apache-solr/example/exampledocs/books.json";
//        String url = "http://api.androidhive.info/feed/feed.json";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0;i<jsonArray.length();i++){//masing2 jsonObject di jsonArray diambil variable "item"
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
//                    result += kuda.length;
//                    result += kuda[10];
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //result = judul_buku.toString();
                tv_fragment_search.setVisibility(View.VISIBLE);

                if (query != null) {
                    view.findViewById(R.id.gv_items_search).setVisibility(View.VISIBLE);
                    String[] web = new String[judul_buku.size()];
                    String[] imageUrl = new String[gambar_buku.size()];
                    web = judul_buku.toArray(web);
                    imageUrl = gambar_buku.toArray(imageUrl);
                    ArrayList<String> buku_judul_search = new ArrayList<>();
                    ArrayList<String> buku_gambar_search = new ArrayList<>();
                    int j = 0;
                    for (int i = 0; i <web.length; i++) {
                        if (web[i] != null && web[i].toLowerCase().contains(query.trim().toLowerCase())) {
                            buku_judul_search.add(j,web[i]);
                            buku_gambar_search.add(j,imageUrl[i]);
                            j++;
                        }
                    }
                    String[] text = new String[buku_judul_search.size()];
                    String[] image = new String[buku_gambar_search.size()];
                    text = buku_judul_search.toArray(text);
                    image = buku_gambar_search.toArray(image);
                    if (text.length == 0) {
                        //TextView textView = (TextView) view.findViewById(R.id.tv_fragment_search);
                        tv_fragment_search.setText("Tidak Ditemukan!");
                    } else {
                        tv_fragment_search.setText("Jumlah ditemukan : "+text.length);

                    }

                    CustomGrid adapter = new CustomGrid(getActivity(), text, image);
                    ExpandableHeightGridView grid = (ExpandableHeightGridView) view.findViewById(R.id.gv_items_search);
//        grid.setVerticalScrollBarEnabled(false);
                    grid.setFocusable(false);
                    grid.setAdapter(adapter);
                    grid.setExpanded(true);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent intent = new Intent(getActivity(), DetailBuku.class);
                            //harusnya diambil dari database//kirim value ke activity detailBuku
                            intent.putExtra("buku_nama", judul_buku.get(+position));
                            intent.putExtra("buku_id_gambar", gambar_buku.get(+position));
                            intent.putExtra("buku_penerbit", penerbit_buku.get(+position));
                            intent.putExtra("buku_terbit", terbit_buku.get(+position));
                            intent.putExtra("buku_pengarang", pengarang_buku.get(+position));
                            startActivity(intent);

                            //Toast.makeText(HalamanDepan.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    view.findViewById(R.id.gv_items_search).setVisibility(View.GONE);
                }
                progressDialog.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(stringRequest);

//

        //doSearch(query);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
