package com.example.mobil_vizora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int KEY = 33;

    Button loginButton, registerButton;
    Animation rotate, blink_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        blink_anim = AnimationUtils.loadAnimation(this, R.anim.blink_anim);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.startAnimation(rotate);
        registerButton.startAnimation(blink_anim);
    }

    public void login(View view) {
        Intent loginintent = new Intent(this, loginActivity.class);

        loginintent.putExtra("seckey", KEY);
        startActivity(loginintent);
    }

    public void register(View view) {
        Intent registerintent = new Intent(this, registerActivity.class);

        registerintent.putExtra("seckey", KEY);
        startActivity(registerintent);
    }

    @Override
    protected void onPause() {
        loginButton.clearAnimation();
        registerButton.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume() {
        loginButton.startAnimation(rotate);
        registerButton.startAnimation(blink_anim);
        super.onResume();
    }
}