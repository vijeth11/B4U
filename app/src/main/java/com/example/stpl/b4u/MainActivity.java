package com.example.stpl.b4u;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomSheetBehavior sheetBehavior;
    private Integer ItemSelected=0;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    View view;
    private String[] name;
    private Double[] cost;
    private String[] Discription;
    private String TAG = MainActivity.class.getSimpleName();
    private String links[];
    private JSONArray items;
    ArrayList<HashMap<String, String>> itemlists;
    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        itemlists = new ArrayList<>();
        gridview = (GridView) findViewById(R.id.gridview);
        new GetContacts().execute();


        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                // TODO Auto-generated method stub
               // Toast.makeText(MainActivity.this, "got", Toast.LENGTH_SHORT).show();
                showDiscription(position);
                return true;
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ImageView tmpImg=v.findViewById(R.id.ticker);
                ImageView tmpImg1 = v.findViewById(R.id.img);
                tmpImg.setVisibility(tmpImg.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
                if (tmpImg.getVisibility()==View.VISIBLE)
                    ItemSelected++;
                else
                    ItemSelected--;
                tmpImg1.setAlpha(tmpImg.getVisibility()==View.VISIBLE?0.5f:1.0f);
                if(ItemSelected==0)
                    findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
                else
                    findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);

            }
        });



        findViewById(R.id.bottomSheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        sheetBehavior= BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        Toast.makeText(MainActivity.this, "Sheet expanded", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        Toast.makeText(MainActivity.this,"sheet collapsed",Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        Button proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           showEatingDialog();
            }
        });



    }

    private void showDiscription(int id)
    {
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("About");
        builder.setMessage(name[id+1]);
       Toast.makeText(MainActivity.this,links[id+1],Toast.LENGTH_SHORT).show();
        view =getLayoutInflater().inflate(R.layout.discription_show,null);
        TextView text = (TextView)view.findViewById(R.id.discriptions);
        text.setText(Discription[id+1]);
        builder.setView(view);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog=builder.create();
        dialog.show();


    }
    private void showEatingDialog() {
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Choose Place");
        builder.setMessage("Where do you want to eat");

        view =getLayoutInflater().inflate(R.layout.payment_chooser,null);
        builder.setView(view);
        builder.setNeutralButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RadioButton parcel=(RadioButton)view.findViewById(R.id.parcel);
                RadioButton isB4u=(RadioButton)view.findViewById(R.id.isB4u);
                if(parcel.isChecked()) {
                    // Toast.makeText(MainActivity.this, "No poytm cash bro plox ;_;", Toast.LENGTH_SHORT).show();
                    Intent menuIntent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(menuIntent);
                }
                else if(isB4u.isChecked()) {
                    Intent menuIntent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(menuIntent);
                    //Toast.makeText(MainActivity.this, "No cash bro plox ;_;", Toast.LENGTH_SHORT).show();
                }
                else {
                    showEatingDialog();
                    Toast.makeText(MainActivity.this, "Select one option buddy", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog=builder.create();
        dialog.show();
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
        if (id == R.id.action_settings) {
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












    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        public Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://vijeth11.000webhostapp.com/index.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    items = jsonObj.getJSONArray("items");

                    // looping through All Contacts
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String cost = c.getString("cost");
                        String link = c.getString("link");
                        String discript = c.getString("discription");


                        // tmp hash map for single contact
                        HashMap<String, String> itemlist = new HashMap<>();

                        // adding each child node to HashMap key => value
                        itemlist.put("id", id);
                        itemlist.put("name", name);
                        itemlist.put("cost", cost);
                        itemlist.put("link", link);
                        itemlist.put("discription",discript);

                        // adding contact to contact list
                        itemlists.add(itemlist);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Toast.makeText(MainActivity.this,"Json parsing error",Toast.LENGTH_SHORT).show();

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                Toast.makeText(MainActivity.this,
                        "Couldn't get json from server. Check LogCat for possible errors!",
                        Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            links=new String[items.length()+1];
            Discription=new String[items.length()+1];
            cost=new Double[items.length()+1];
            name=new String[items.length()+1];
            Log.e(TAG,"onPostExecute is running");
            int count=0;
            for(HashMap<String,String>map:itemlists){
                count++;
                for(Map.Entry<String,String>mapEntry:map.entrySet())
                {
                    String key = mapEntry.getKey();
                    String value=mapEntry.getValue();
                    if(key=="link")
                        links[count] = value;
                    if(key=="name")
                        name[count]=value;
                    if(key=="cost")
                        cost[count]=Double.parseDouble(value);
                    if(key=="discription")
                        Discription[count]=value;
                }
            }

            gridview.setAdapter(new ImageAdapter(MainActivity.this, Arrays.copyOfRange(links, 1, links.length),Arrays.copyOfRange(name, 1, name.length)));

        }
    }
}
