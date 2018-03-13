package com.example.stpl.b4u;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pnikosis.materialishprogress.ProgressWheel;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final ProgressWheel wheel = findViewById(R.id.progress_wheel);
        Button b1 = findViewById(R.id.click);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheel.setVisibility(View.INVISIBLE);
                findViewById(R.id.ticker1).setVisibility(View.VISIBLE);
            }
        });
    }
}
