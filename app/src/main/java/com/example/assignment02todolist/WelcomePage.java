package com.example.assignment02todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class WelcomePage extends AppCompatActivity {
    //Animations
    Animation topAnimation, bottomAnimation, middleAnimation;
    View first, second, third, fourth, fifth, sixth;
    TextView text, welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome_page);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation= AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.third_line);
        fourth = findViewById(R.id.fourth_line);
        fifth= findViewById(R.id.fifth_line);
        sixth = findViewById(R.id.sixth_line);

        text = findViewById(R.id.text);
        welcomeText = findViewById(R.id.welcomeText);

        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        text.setAnimation(middleAnimation);
        welcomeText.setAnimation(bottomAnimation);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainActivity = new Intent(WelcomePage.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        }, 3000);
    }
}