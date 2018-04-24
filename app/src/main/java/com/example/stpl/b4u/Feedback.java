package com.example.stpl.b4u;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.get_item:
                RatingBar rb = (RatingBar) findViewById(R.id.ratingbar);
                MultiAutoCompleteTextView ml = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
                String feedback=ml.getText().toString().replaceAll("(\\n)+", "\\\\n");
                feedback=feedback.replace(" ","+");
                String rating=String.valueOf(rb.getRating());
                String url="https://vijeth11.000webhostapp.com/?feedback="+feedback+"&rating="+rating;
                SendFeedback(url);
                //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

    public  void SendFeedback(String JSON_URL)
    {
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
                            String error =obj.getString("success");
                            String message=obj.getString("message");
                            if(Objects.equals(error, "1")) {

                                Toast.makeText(Feedback.this, message, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Feedback.this, message, Toast.LENGTH_SHORT).show();
                                finish();

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
