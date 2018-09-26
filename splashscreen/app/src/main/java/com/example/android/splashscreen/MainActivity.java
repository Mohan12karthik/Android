package com.example.android.splashscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img=(ImageView)findViewById(R.id.flash);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fast);
        img.startAnimation(animation);


        Thread timer=new Thread(){
            @Override
            public void run() {

                try{

                    sleep(5000);

                    Intent intent=new Intent(getApplicationContext(),home.class);
                    startActivity(intent);
                    finish();

                    super.run();
                }catch (InterruptedException e){
                        e.printStackTrace();
                }


            }
        };
        timer.start();

    }
}
