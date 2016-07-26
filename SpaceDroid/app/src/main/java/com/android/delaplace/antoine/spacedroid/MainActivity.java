package com.android.delaplace.antoine.spacedroid;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Stay activity in  portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        RelativeLayout principalLayout = (RelativeLayout) findViewById(R.id.layout);
        principalLayout.setBackgroundResource(R.drawable.background);

        ImageView textPlay = (ImageView) findViewById(R.id.text_play);
        ImageView logo = (ImageView) findViewById(R.id.logo);

        textPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send Intent to the other activity
                Intent t = new Intent(MainActivity.this, GameActivity.class);
                startActivity(t);
            }
        });

        logo.setImageResource(R.drawable.logo);
        textPlay.setImageResource(R.drawable.text_play);

    }
}
