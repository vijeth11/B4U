package com.example.stpl.b4u;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by stpl on 12/3/18.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;

    public ImageAdapter(Context c,LayoutInflater layoutInflater) {
        mContext = c;
        inflater=layoutInflater;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public Double getCost (int position){return  cost[position];}
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.grid_elements, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        imageView.setImageResource(mThumbIds[position]);
        textView.setText(name[position]);
        return rowView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_8, R.drawable.sample_9,
            R.drawable.sample_10
    };
    private String[] name={
            "Chicken DUM Biryani (Basmati Rice)","Biryani Rice (Kushka) (Basmati Rice)",
            "Chicken Kabab","B4U Special Crispy Chicken",
            "Chicken Kalmi Kebab","Boiled egg",
            "Vegetable Biryani (Basmati Rice)","Mutton DUM Biryani (Sunday Special) ",
            "Lassi","Guntur Chicken Curry(spicy)",
            "Bakasur Combo"

    };
    private Double[] cost={
            90.0,60.0,
            40.0,60.0,
            50.0,10.0,
            70.0,140.0,
            30.0,70.0,
            120.0
    };

}
