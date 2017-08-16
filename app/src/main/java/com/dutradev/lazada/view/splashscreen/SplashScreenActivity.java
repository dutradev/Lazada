package com.dutradev.lazada.view.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dutradev.lazada.R;
import com.dutradev.lazada.view.home.HomeActivity;

/**
 * Created by dutradev on 15/08/2017.
 */

public class SplashScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_layout);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){

                }finally {
                    Intent iTrangChu = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(iTrangChu);
                }
            }
        });

        thread.start();

    }
}
