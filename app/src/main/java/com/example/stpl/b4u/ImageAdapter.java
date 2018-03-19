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
    public ImageAdapter()
    {

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

    public String getDiscription(int position){return Discription[position];}

    public String getName(int position) {return name[position];}

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
    private String[] Discription={"Chicken Dum Biryani is one of the delicious chicken recipes that is prepared by cooking With Quality Basmati Rice, High Quality Skinout Chicken & Quality Masala with steam (the literal meaning of dum pukth). Loaded with Nandini Ghee, this Indian recipe is known for its aroma",
    "This is plain biryani rice flavored with chicken stock mostly. The advantage of ordering kuska is that you can have it with a raita and pickle and it yet makes you feel you are having a proper biryani so it is economical. Secondly there is always the option of ordering curries of your choice if you skip the meat part of the biryani with plain biryani rice. Some have kuska with kebabs while some prefer a simple curry like salan or korma.",
    "This is plain biryani rice flavored with chicken stock mostly. The advantage of ordering kuska is that you can have it with a raita and pickle and it yet makes you feel you are having a proper biryani so it is economical. Secondly there is always the option of ordering curries of your choice if you skip the meat part of the biryani with plain biryani rice. Some have kuska with kebabs while some prefer a simple curry like salan or korma.",
    "Let me walk you through the pleasure of eating this crispy, juicy fried chicken: You've got this glistening drumstick with a coating so full of crunchy ripples that it is hard to determine where to hold on. You decide fingertips are best for maneuvering this chicken from the plate to your mouth.",
    "The cuisine of South Asia is not just a trail of curries but the people here are equally crazy about Kebabs. Though the process of cooking kebabs sounds very rustic and basic but in Indian subcontinent it has become a sublime art which is passed on from one generation to another",
    "Boiled eggs are eggs cooked with their shells unbroken, usually by immersion in boiling water. Hard-boiled eggs are cooked so that the egg white and egg yolk both solidify, while for a soft-boiled egg",
    "Biryani , in its simplest form is an preparation in which rice and meat/vegetables are cooked together in a sealed container over very slow flame. Biryani is an aromatic and rich rice-based food made with spices andmeat/vegetables that is cooked on very slow flame in a sealed container( known as Dum)",
    "Hyderabadi Mutton Dum Biryani. Hyderabadi mutton dum biryani or kachche gosht ki dum biryani is a traditional slow cooking method. This style of makingbiryani by cooking the raw meat with spices for 45 minutes on slow flame with the rice and kept on dum until done",
    "The pride and joy of Punjabi cuisine - the mighty Patiala-peg lassi, is probably one of the most loved beverages during the sweltering Indian summer. Smooth and creamy, the yoghurt-based refreshment , traditionally served in earthen pots called matkas, has miraculous powers that can cool you instantly, and is probably the reason why even in Ayurveda, it is considered to be a great summer cooler.",
    "Guntur chicken is known for it spiciness and the rich taste of masala. Gundur chicken imbibes all the flavor of the famous Guntur chili.",
    "jpgChicken Dum Biryani + Kabab 1pc + Guntur Chicken Curry 1pc + Cool Drinks(200ml) +1 Boiled egg"};
}
