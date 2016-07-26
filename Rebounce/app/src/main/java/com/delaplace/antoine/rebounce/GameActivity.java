package com.delaplace.antoine.rebounce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView mGameView = new GameView(this);
        setContentView(mGameView);
    }
}
