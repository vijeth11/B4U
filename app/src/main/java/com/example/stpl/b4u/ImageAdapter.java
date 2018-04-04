package com.example.stpl.b4u;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.stpl.b4u.MainActivity.ItemSelected;
import static com.example.stpl.b4u.MainActivity.selected;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public String [] mThumbIds ;
    public String [] name  ;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    View view;
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
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


          if(convertView==null) {
              LayoutInflater inflater = (LayoutInflater) mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

              View rowView = inflater.inflate(R.layout.grid_elements, parent, false);
              TextView textView = (TextView) rowView.findViewById(R.id.text);
              ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

              Picasso.with(mContext)
                      .load(mThumbIds[position])
                      .into(imageView);
              textView.setText(name[position]);

              rowView.setOnLongClickListener(new View.OnLongClickListener() {
                  public boolean onLongClick(View v) {
                      // TODO Auto-generated method stub
                      // Toast.makeText(MainActivity.this, "got", Toast.LENGTH_SHORT).show();
                       showDiscription(position);
                      return true;
                  }
              });

              rowView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      ImageView tmpImg=v.findViewById(R.id.ticker);
                      ImageView tmpImg1 = v.findViewById(R.id.img);
                      Button minus= v.findViewById(R.id.minus);
                      Button plus = v.findViewById(R.id.plus);
                      final TextView quant = v.findViewById(R.id.qual);

                      tmpImg.setVisibility(tmpImg.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
                      minus.setVisibility(minus.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
                      plus.setVisibility(plus.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
                      quant.setVisibility(quant.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
                      quant.setText("1");
                      MainActivity.quant[position]=1;
                      if (tmpImg.getVisibility()==View.VISIBLE) {
                          ItemSelected++;
                          selected[position]=true;
                      }
                      else {
                          ItemSelected--;
                          selected[position]=false;

                      }
                      tmpImg1.setAlpha(tmpImg.getVisibility()==View.VISIBLE?0.5f:1.0f);
                      if(ItemSelected==0)
                          MainActivity.bottomSheet.setVisibility(View.GONE);
                      else
                          MainActivity.bottomSheet.setVisibility(View.VISIBLE);

                      update();

                      minus.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              if(MainActivity.quant[position]-1>0)
                              {
                                  MainActivity.quant[position]-=1;
                                  quant.setText(String.valueOf(Integer.parseInt(quant.getText().toString())-1));
                              }
                              update();
                          }
                      });

                      plus.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              MainActivity.quant[position]+=1;
                              quant.setText(String.valueOf(Integer.parseInt(quant.getText().toString())+1));
                              update();
                          }
                      });


                  }
              });

              return rowView;
          }
          else
              return convertView;

    }


    public void update()
    {
        String selectedItemList="";
        double Total=0;
        for(int i=0;i<selected.length-1;i++)
            if(selected[i]) {
                selectedItemList += name[i] + "      quantity = "+String.valueOf(MainActivity.quant[i])+"\n";
                Total += MainActivity.cost[i+1]*MainActivity.quant[i];
            }

        MainActivity.itemlist.setText(selectedItemList);
        MainActivity.totalCost.setText("₹"+String.valueOf(Total));
    }
    public  void showDiscription(int id)
    {
        builder=new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("About");
        builder.setMessage(name[id]);
//       Toast.makeText(MainActivity.this,links[id+1],Toast.LENGTH_SHORT).show();
        view =((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.discription_show,null);
        TextView text = (TextView)view.findViewById(R.id.discriptions);
        text.setText(MainActivity.Discription[id+1]);
        builder.setView(view);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog=builder.create();
        dialog.show();


    }
}
