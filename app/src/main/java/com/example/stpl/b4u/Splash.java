package com.example.stpl.b4u;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final VideoView splash =(VideoView) findViewById(R.id.splash);
        splash.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.starting));
        //splash.setVideoURI(Uri.parse("http://abhiandroid-8fb4.kxcdn.com/ui/wp-content/uploads/2016/04/videoviewtestingvideo.mp4"));
        splash.start();
        splash.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
                    public void onCompletion(MediaPlayer mp){
                splash.start();
            }
        });

//        splash.setOnPreparedListener (new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//            }
//        });
    }
}
