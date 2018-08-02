package com.example.root.ternaknesia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewSignIn;
    private ImageView mainImage;
    private TextView textView;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog =new ProgressDialog(this);
        //buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignin);
        mainImage = (ImageView) findViewById(R.id.mainImage);
        textView = (TextView) findViewById(R.id.textView);
        //buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransision);

        textView.startAnimation(animation);
        mainImage.startAnimation(animation);
        textViewSignIn.startAnimation(animation);

        final Intent intent = new Intent(this, login.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }

            }
        };

        timer.start();

    }

    @Override
    public void onClick(View view) {
        /*if(view == buttonRegister){
            //start activity register
            finish();
            startActivity(new Intent(this, login.class));
        }*/
        if(view == textViewSignIn){
            //start activity login
            /*finish();
            startActivity(new Intent(this, login.class));*/
        }
    }
}
