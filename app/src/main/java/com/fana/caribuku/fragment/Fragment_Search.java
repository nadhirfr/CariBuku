package com.fana.caribuku.fragment;

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

import com.fana.caribuku.activity.DetailBuku;
import com.fana.caribuku.activity.HalamanDepan;
import com.fana.caribuku.adapter.CustomGrid;
import com.fana.caribuku.adapter.ExpandableHeightGridView;
import com.fana.caribuku.R;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            query = getArguments().getString("query",null);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment__search, container, false); //pass the correct layout name for the fragment

//        TextView text = (TextView) view.findViewById(R.id.test);
//        text.setText(query);
        if (query!=null){
            view.findViewById(R.id.gv_items_search).setVisibility(View.VISIBLE);
            HalamanDepan halamanDepan = new HalamanDepan();
            final String[] web = halamanDepan.web;
            final String[] text = new String[web.length];
            final int[] imageId = halamanDepan.imageId;
            final int[] image =  new int[imageId.length];
            int j = 0;
            for (int i = 0; i<=web.length-1; i++){
                if (web[i] != null && web[i].toLowerCase().contains(query.trim().toLowerCase())){
                    text[j] = web[i];
                    image[j] = imageId[i];
                    j++;
                }
            }
            if (text[0]==null){
                TextView textView = (TextView) view.findViewById(R.id.tv_fragment_search);
                textView.setText("Tidak Ditemukan!");
            }
            CustomGrid adapter = new CustomGrid(getActivity(), text, image);
            ExpandableHeightGridView grid= (ExpandableHeightGridView) view.findViewById(R.id.gv_items_search);
//        grid.setVerticalScrollBarEnabled(false);
            grid.setFocusable(false);
            grid.setAdapter(adapter);
            grid.setExpanded(true);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(getActivity(),DetailBuku.class);
                    //harusnya diambil dari database
                    intent.putExtra("buku_nama",text[+ position]);
                    intent.putExtra("buku_id_gambar",image[+ position]);
                    startActivity(intent);

                    //Toast.makeText(HalamanDepan.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            view.findViewById(R.id.gv_items_search).setVisibility(View.GONE);
        }

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
