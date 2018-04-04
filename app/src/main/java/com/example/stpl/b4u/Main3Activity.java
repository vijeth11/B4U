package com.example.stpl.b4u;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Main3Activity extends AppCompatActivity {

    private  ProgressWheel wheel=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        wheel = findViewById(R.id.progress_wheel);
        String txid = getIntent().getStringExtra("txid");
        String orders = getIntent().getStringExtra("orders").toString();
        orders=orders.replaceAll("(\\n)+", "\\\\n");
        String urls="https://vijeth11.000webhostapp.com/?txid="+txid+"&cost=200&order="+orders.replace(" ","+");

        //Toast.makeText(Main3Activity.this,urls,Toast.LENGTH_LONG).show();
        updateTheData(urls);


    }

    private void updateTheData(String JSON_URL) {
        //getting the progressbar


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion



                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            String error =obj.getString("error");
                            String message=obj.getString("message");
                            if(Objects.equals(error, "TRUE")) {
                                Toast.makeText(Main3Activity.this, message, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                wheel.setVisibility(View.INVISIBLE);
                                findViewById(R.id.ticker1).setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Main3Activity.this.finish();
                                    }
                                }, 2000);
                            }
                            //now looping through all the elements of the json array


                            //creating custom adapter object


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
