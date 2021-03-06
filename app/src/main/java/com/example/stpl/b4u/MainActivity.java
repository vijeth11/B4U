package com.example.stpl.b4u;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
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
import android.widget.WrapperListAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PaytmPaymentTransactionCallback {

    private BottomSheetBehavior sheetBehavior;
    public static Integer ItemSelected=0;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    View view;
    public static int[] quant;
    public  static String[] name;
    public static Double[] cost;
    public static  String[] Discription;
    private String TAG = MainActivity.class.getSimpleName();
    private String links[];
    public static Boolean selected[];
    private JSONArray items;
    public static  TextView orderlist,totalCost ;
    public static LinearLayout bottomSheet;
    ArrayList<HashMap<String, String>> itemlists;
    HashMap<String, String> itemlist;
    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        totalCost=(TextView) findViewById(R.id.ItemTotalCost);
        orderlist =(TextView) findViewById(R.id.orders);
        bottomSheet=findViewById(R.id.bottom_sheet);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        itemlists = new ArrayList<>();
        gridview = (GridView) findViewById(R.id.gridview);
        new GetItems().execute();
        startActivityForResult(new Intent(MainActivity.this,Splash.class),1010);


//


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
                       // Toast.makeText(MainActivity.this, "Sheet expanded", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                       // Toast.makeText(MainActivity.this,"sheet collapsed",Toast.LENGTH_SHORT).show();
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


    private void showEatingDialog() {
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Choose Place");
        builder.setMessage("Where do you want to eat");

        view =getLayoutInflater().inflate(R.layout.payment_chooser,null);
        builder.setView(view);
        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }

        });
        builder.setNegativeButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    RadioButton parcel=(RadioButton)view.findViewById(R.id.parcel);
                    RadioButton inB4u=(RadioButton)view.findViewById(R.id.isB4u);
                    if(parcel.isChecked()) {
                        // Toast.makeText(MainActivity.this, "No poytm cash bro plox ;_;", Toast.LENGTH_SHORT).show();
                        generateCheckSum();
                        Intent menuIntent = new Intent(MainActivity.this, Main2Activity.class).putExtra("orders","Parcel:\n"+orderlist.getText().toString()).putExtra("totalCost",totalCost.getText().toString());
                        startActivity(menuIntent);
                    }
                    else if(inB4u.isChecked()) {
                        generateCheckSum();
                        Intent menuIntent = new Intent(MainActivity.this, Main2Activity.class).putExtra("orders",orderlist.getText().toString()).putExtra("totalCost",totalCost.getText().toString());
                        startActivity(menuIntent);
                        //Toast.makeText(MainActivity.this, "No cash bro plox ;_;", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        showEatingDialog();
                        Toast.makeText(MainActivity.this, "Select one option buddy", Toast.LENGTH_SHORT).show();

                }
            }
        });
        dialog=builder.create();
        dialog.show();
    }
    private void generateCheckSum() {

        //getting the tax amount first.
        String txnAmount = totalCost.getText().toString().trim();
        txnAmount=String.valueOf((int)Float.parseFloat(txnAmount.replace("₹","")));
        //creating a retrofit object.
        txnAmount+=".00";

        Toast.makeText(MainActivity.this,"amount at generateChecksum="+txnAmount,Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                Toast.makeText(MainActivity.this,"amount at initpayment" + "="+paytm.getTxnAmount(),Toast.LENGTH_SHORT).show();
                initializePaytmPayment(response.body().getChecksumHash(), paytm);

            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }
        });


   }


    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();


        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);


        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly
    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this, bundle.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
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

        if (id == R.id.About) {
            AlertDialog dialog;
            AlertDialog.Builder builder;
            builder=new AlertDialog.Builder(MainActivity.this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("About Us");
            builder.setMessage("B4U");
            View v=((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.discription_show,null);
            TextView text = (TextView)v.findViewById(R.id.discriptions);
            text.setText("yet to be filled");
            builder.setView(v);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog=builder.create();
            dialog.show();


        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://drive.google.com/open?id=1vTGuzkEUvzHabAVzBz8qJa6ymXvbzNz1");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.feedback) {
            Intent feedback=new Intent(this,Feedback.class);
            startActivity(feedback);
            //Toast.makeText(this,"yet to be added ",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }












    private class GetItems extends AsyncTask<Void, Void, Void> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();


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
                        itemlist = new HashMap<>();

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
            links=new String[itemlists.size()];
            Discription=new String[itemlists.size()];
            cost=new Double[itemlists.size()];
            name=new String[itemlists.size()];
            selected=new Boolean[itemlists.size()];
            quant =new int[itemlists.size()];
            Log.e(TAG,"onPostExecute is running");
            int count=0;
            for(HashMap<String,String>map:itemlists){
                selected[count]=false;
                quant[count]=0;
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
                count++;
            }


            gridview.setAdapter(new ImageAdapter(MainActivity.this, links,name));
            itemlists.remove(itemlist);
            finishActivity(1010);
        }
    }


}
