package com.example.yurim.dormtakki;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler(){
            @Override
            public void handleMessage(Message msg){
                finish();
            }

        };
        hd.sendEmptyMessageDelayed(0,3000);
    }



}