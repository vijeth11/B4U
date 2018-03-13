package com.example.stpl.b4u;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomSheetBehavior sheetBehavior;
    private ImageAdapter imageAdapter;
    private Integer ItemSelected=0;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    View view;

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

        GridView gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter=new ImageAdapter(this,getLayoutInflater());
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "got", Toast.LENGTH_SHORT).show();
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
                showPaymentDialog();
            }
        });



    }

    private void showPaymentDialog() {
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Title");
        builder.setMessage("Message");

        view =getLayoutInflater().inflate(R.layout.payment_chooser,null);
        builder.setView(view);
        builder.setNeutralButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RadioButton paytmRadio=(RadioButton)view.findViewById(R.id.paytmMode);
                RadioButton cashRadio=(RadioButton)view.findViewById(R.id.cashMode);
                if(paytmRadio.isChecked()) {
                    Toast.makeText(MainActivity.this, "No poytm cash bro plox ;_;", Toast.LENGTH_SHORT).show();
                    Intent menuIntent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(menuIntent);
                }
                else if(cashRadio.isChecked())
                    Toast.makeText(MainActivity.this, "No cash bro plox ;_;", Toast.LENGTH_SHORT).show();
                else {
                    showPaymentDialog();
                    Toast.makeText(MainActivity.this, "Select one plox", Toast.LENGTH_SHORT).show();
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
}
