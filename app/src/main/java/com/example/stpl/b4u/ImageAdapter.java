package com.example.stpl.b4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public String [] mThumbIds ;
    public String [] name  ;
    // Constructor
    public ImageAdapter(Context c,String[] links,String[] names){
        mContext = c;
        mThumbIds=links;
        name=names;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.grid_elements, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

           Picasso.with(mContext)
                   .load(mThumbIds[position])
                   .into(imageView);
           textView.setText(name[position]);

        return rowView;
    }
}
