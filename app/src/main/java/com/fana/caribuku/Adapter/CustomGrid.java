package com.fana.caribuku.adapter;

import android.content.Context;
import android.net.Network;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fana.caribuku.R;
import com.fana.caribuku.singleton.VolleySingleton;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    ImageLoader mImagesLoader;
    ArrayList<String> gambar;
    private final String[] web;
//    private final int[] Imageid;
    private final String[] ImageUrl;

    public CustomGrid(Context c,String[] web,String[] ImageUrl ) {
        mContext = c;
        //this.Imageid = Imageid;
        this.ImageUrl = ImageUrl;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        mImagesLoader = VolleySingleton.getInstance(this.mContext).getImageLoader();
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_item_text);
            textView.setText(web[position]);

            NetworkImageView imageView = (NetworkImageView)grid.findViewById(R.id.grid_item_image);
//            imageView.setImageResource(Imageid[position]);
            imageView.setErrorImageResId(R.drawable.matematika);
            imageView.setDefaultImageResId(R.drawable.inggris);
            imageView.setImageUrl(ImageUrl[position],mImagesLoader);


//            imageView.setImageUrl();
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}