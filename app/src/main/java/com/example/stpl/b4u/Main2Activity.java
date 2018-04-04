package com.example.stpl.b4u;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    FrameLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main2);
        rootLayout = (FrameLayout) findViewById(R.id.root_layout);
        final EditText edt1 = (EditText) findViewById(R.id.editText);
        TextView text = (TextView) findViewById(R.id.cost);
        text.setText(getIntent().getStringExtra("totalCost"));
        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }

        Button button =findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Main2Activity.this,edt1.getText(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this,Main3Activity.class).putExtra("txid",edt1.getText().toString()).putExtra("orders",getIntent().getStringExtra("orders")).putExtra("totalCost",getIntent().getStringExtra("totalCost")));
                finish();
            }
        });
    }
    private void circularRevealActivity() {

        int cx = 0;
        int cy = 0;

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight()/4);

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(500);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();
    }
}
